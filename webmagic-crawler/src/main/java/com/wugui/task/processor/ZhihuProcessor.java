package com.wugui.task.processor;

import cn.hutool.core.text.UnicodeUtil;
import com.wugui.task.HttpClientDownloader;
import com.wugui.utils.GenerateUserAgentUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 知乎用户爬虫 todo
 *
 * 1. 获取个人首页动态页面 https://www.zhihu.com/people/xiao-sui-feng-13/activities
 *    1.1 可以通过该页面获取所在行业，个人简介，页面内包含了一大串json，能获取不少数值信息（点赞数，感谢次数等）
 *
 *
 * 2. 获取用户的关注人：
 *    2.1 可以使用接口：https://www.zhihu.com/api/v4/members/xiao-sui-feng-13/followees?include=data[*].answer_count,articles_count,gender,follower_count,is_followed,is_following,badge[?(type=best_answerer)].topics&offset=0&limit=20
 *
 *    2.2 直接访问页面：https://www.zhihu.com/people/xiao-sui-feng-13/following
 *                    https://www.zhihu.com/people/xiao-sui-feng-13/following?page=2
 *
 *
 * 3. 获取个人首页关注的问题页面 https://www.zhihu.com/people/cao-jiu-gu-niang-99/following/questions
 *
 */
public class ZhihuProcessor implements PageProcessor {
    private Site site = Site.me();


    @Override
    public void process(Page page) {

        // 将unicode编码转成中文
        System.out.println(UnicodeUtil.toString(page.getJson().get()));
    }


    @Override
    public Site getSite() {
        return site
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
    }


    public static void main(String[] args) {
        Spider.create(new ZhihuProcessor())
                // 设置download解决官方webMagic无法访问部分ssl网站问题
                .setDownloader(new HttpClientDownloader())
//                .addUrl("https://www.zhihu.com/api/v4/members/xiao-sui-feng-13/followees?include=data[*].answer_count,articles_count,gender,follower_count,is_followed,is_following,badge[?(type=best_answerer)].topics&offset=0&limit=20")
//                .addUrl("https://www.zhihu.com/people/cao-jiu-gu-niang-99/following/questions")
                .addUrl("https://www.zhihu.com/people/xiao-sui-feng-13/following?page=2")
                .thread(1)
                .run();
    }
}
