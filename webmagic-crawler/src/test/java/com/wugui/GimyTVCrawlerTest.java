package com.wugui;

import com.wugui.task.HttpClientDownloader;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Webmagic测试
 * 对视频网站 https://cn.gimy.tv/ 进行爬取工作
 *
 * @program: java-crawler
 * @author: huzekang
 * @create: 2019-06-16 23:16
 **/
@Slf4j
public class GimyTVCrawlerTest {

    @Test
    public void test() {
        Spider.create(new PageProcessor() {
            @Override
            public void process(Page page) {
                //  获取当页动漫列表
                log.info(page.getHtml().xpath("//*[@id=\"content\"]/li").toString());
            }

            @Override
            public Site getSite() {
                return Site.me();
            }
        })
                // 设置download解决官方webMagic无法访问部分ssl网站问题
                .setDownloader(new HttpClientDownloader())
                //  设置动漫类别视频列表的第一页为起始
                .addUrl("https://cn.gimy.tv/list/anime-----hits_week-1.html")
                .thread(1)
                .run();
    }
}
