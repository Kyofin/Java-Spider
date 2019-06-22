package com.wugui.task.processor;

import com.wugui.task.HttpClientDownloader;
import lombok.extern.slf4j.Slf4j;
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
public class GimyTVCrawlerProcessor implements PageProcessor {


    private Site site = Site.me();


    @Override
    public void process(Page page) {
        //  获取当页动漫列表
        log.info(page.getHtml().xpath("//*[@id=\"content\"]/li").toString());
    }


    @Override
    public Site getSite() {
        return site
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
    }


    public static void main(String[] args) {
        // 设置download解决官方webMagic无法访问部分ssl网站问题
        Spider.create(new GimyTVCrawlerProcessor())
                .setDownloader(new HttpClientDownloader())
                //  设置动漫类别视频列表的第一页为起始
                .addUrl("https://cn.gimy.tv/list/anime-----hits_week-1.html")
                .thread(1)
                .run();
    }
}
