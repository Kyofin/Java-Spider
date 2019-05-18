package com.wugui.task.processor;

import com.google.common.collect.Lists;
import com.wugui.pojo.GithubRepo;
import com.wugui.pojo.GithubUser;
import com.wugui.task.HttpClientDownloader;
import com.wugui.task.pipeline.GithubRepoJPAPipeline;
import com.wugui.task.pipeline.GithubUserJPAPipeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.RedisScheduler;

import java.util.Objects;

/**
 * @description: 对github爬虫，快速体验weblogic
 *
 * xpath获取按标签属性和文本
 *              /li/a/@herf 这样取的应该是herf的内容
 *              /li/a/text() 这样取得是text内容
 *
 * webmagic常用抽取语法
 *              tidyText()
 *
 * @author: huzekang
 * @create: 2019-05-18 10:27
 **/
@Component
public class GithubRepoProcessor implements PageProcessor {

    @Autowired
    private GithubRepoJPAPipeline githubRepoJPAPipeline;
    @Autowired
    private GithubUserJPAPipeline githubUserJPAPipeline;


    private Site site = Site.me();

    /**
     * 项目仓库页面
     */
    public static final String INDEX_PAGE_URL = "(https://github\\.com/\\w+)";
    /**
     * 用户首页
     */
    public static final String REPO_PAGE_URL = "(https://github\\.com/\\w+/\\w+)";

    @Override
    public void process(Page page) {
        // 获取项目地址放入schedule
        page.addTargetRequests(page.getHtml().links().regex("https://github\\.com/\\w+/\\w+").all());
        // 获取扫描到的首页地址放入schedule
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+)").all());

        // 区别用户首页和项目页面
        if (page.getUrl().regex(REPO_PAGE_URL).match()) {
            // 解析页面内容
            String repoAuthor = page.getHtml().xpath("//*[@id=\"js-repo-pjax-container\"]/div[1]/div/h1/span[1]/a/text()").get();
            String repoName = page.getHtml().xpath("//*[@id=\"js-repo-pjax-container\"]/div[1]/div/h1/strong/a/text()").get();
            String readme = page.getHtml().xpath("//div[@id='readme']/tidyText()").get();
            // 如果获取不到仓库名，则放弃该页面
            if (Objects.isNull(repoName)) {
                page.setSkip(true);
            } else {
            // 将解析内容放入pipeline
                GithubRepo githubRepo = new GithubRepo()
                        .setRepoAuthor(repoAuthor)
                        .setRepoName(repoName)
                        .setRepoReadme(readme)
                        .setUrl(page.getRequest().getUrl());

                page.putField("githubRepo", githubRepo);
            }
        }
        else if (page.getUrl().regex(INDEX_PAGE_URL).match()) {
            // 提取用户信息(先用css定位元素，再用xpath获取内容或者属性)
            String userName = page.getHtml().css(".p-name").xpath("tidyText()").get();
            String userAvatar = page.getHtml().css("#js-pjax-container > div > div.h-card.col-3.float-left.pr-6 > a > img").xpath("img/@src").get();
            String userNickname = page.getHtml().css(".p-nickname").xpath("tidyText()").get();
            String userProfile = page.getHtml().css("#js-pjax-container > div > div.h-card.col-3.float-left.pr-6 > div.js-profile-editable-area > div > div").xpath("tidyText()").get();
            String userMention = page.getHtml().css(".user-mention").xpath("tidyText()").get();
            String userLabel = page.getHtml().css(".p-label").xpath("tidyText()").get();

            // 如果获取不到用户名，则放弃该页面
            if (Objects.isNull(userName)) {
                page.setSkip(true);
            }else {
                GithubUser githubUser = new GithubUser()
                        .setUserName(userName)
                        .setUserAvatar(userAvatar)
                        .setUserNickName(userNickname)
                        .setUserProfile(userProfile)
                        .setUserMention(userMention)
                        .setUserLabel(userLabel)
                        .setUrl(page.getRequest().getUrl());

                page.putField("githubUser", githubUser);
            }

        }

    }

    @Override
    public Site getSite() {
        return site;
    }


    public  void start() {
        Spider.create(new GithubRepoProcessor())
                // 设置download解决官方webMagic无法访问部分ssl网站问题
                .setDownloader(new HttpClientDownloader())
                .addUrl("https://github.com/huzekang/")
                .setScheduler(new RedisScheduler(new JedisPool("127.0.0.1",6379)))
                .setPipelines(Lists.newArrayList(githubRepoJPAPipeline,githubUserJPAPipeline))
                .thread(8)
                .runAsync();

    }
}
