package com.wugui.task;

import com.google.common.collect.Lists;
import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.RedisScheduler;

import java.util.Objects;

/**
 * @description: 对github爬虫，快速体验weblogic
 * @author: huzekang
 * @create: 2019-05-18 10:27
 **/
public class GithubRepoProcessor implements PageProcessor {
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
            String repoAuthor = page.getHtml().xpath("//*[@id=\"js-repo-pjax-container\"]/div[1]/div/h1/span[1]/tidyText()").get();
            String repoName = page.getHtml().xpath("//*[@id=\"js-repo-pjax-container\"]/div[1]/div/h1/strong/a/tidyText()").get();
            String readme = page.getHtml().xpath("//div[@id='readme']/tidyText()").get();
            // 如果获取不到仓库名，则放弃该页面
            if (Objects.isNull(repoName)) {
                page.setSkip(true);
            } else {
            // 将解析内容放入pipeline
            page.putField("repo_author", repoAuthor);
            page.putField("repo_name", repoName);
            page.putField("repo_readme", readme);
            }
        }
        else if (page.getUrl().regex(INDEX_PAGE_URL).match()) {
            // 如果获取不到用户名，则放弃该页面
            if (Objects.isNull(page.getHtml().css(".p-name").get())) {
                page.setSkip(true);
            }else {

                // 提取用户信息(先用css定位元素，再用xpath获取内容或者属性)
                page.putField("user_name", page.getHtml().css(".p-name").xpath("tidyText()").get());
                page.putField("user_avatar", page.getHtml().css("#js-pjax-container > div > div.h-card.col-3.float-left.pr-6 > a > img").xpath("img/@src").get());
                page.putField("user_nickname", page.getHtml().css(".p-nickname").xpath("tidyText()").get());
                page.putField("user_profile", page.getHtml().css("#js-pjax-container > div > div.h-card.col-3.float-left.pr-6 > div.js-profile-editable-area > div > div").xpath("tidyText()").get());
                page.putField("user_mention", page.getHtml().css(".user-mention").xpath("tidyText()").get());
                page.putField("user_label", page.getHtml().css(".p-label").xpath("tidyText()").get());
            }

        }

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new GithubRepoProcessor())
                // 设置download解决官方webMagic无法访问部分ssl网站问题
                .setDownloader(new HttpClientDownloader())
                .addUrl("https://github.com/huzekang/")
                .setScheduler(new RedisScheduler(new JedisPool("127.0.0.1",6379)))
                .setPipelines(Lists.newArrayList(new ConsolePipeline(),new FilePipeline("./data")))
                .thread(16)
                .start();

    }
}
