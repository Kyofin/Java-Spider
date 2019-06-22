package com.wugui.task.processor;

import com.wugui.task.HttpClientDownloader;
import com.wugui.utils.GenerateUserAgentUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 *
 * 马蜂窝旅行网站爬虫 todo
 *
 * 注意： 该网站需要先访问一次足迹网页(或者其他的)，之后携带返回的cookie才能访问个人首页
 *
 * 1. 获取个人首页 http://www.mafengwo.cn/u/72908311.html
 *   1.1 提前有效信息
 *   1.2 将关注者的首页加入爬取队列
 *   1.3 将这个人的足迹页面加入爬取队列
 *
 * 2. 获取个人足迹 http://www.mafengwo.cn/path/72908311.html
 */
public class MeFengWoProcessor implements PageProcessor {


    private Site site = Site.me();


    @Override
    public void process(Page page) {

        System.out.println(page.getHtml());
    }


    @Override
    public Site getSite() {
        return site
                .setUserAgent(GenerateUserAgentUtil.getRandomUserAgent());
    }


    public static void main(String[] args) {
        Spider.create(new MeFengWoProcessor())
                // 设置download解决官方webMagic无法访问部分ssl网站问题
                .setDownloader(new HttpClientDownloader())
                .addUrl("http://www.mafengwo.cn/path/72908311.html")
                .addUrl("http://www.mafengwo.cn/u/72908311.html")
                .thread(1)
                .run();
    }
}
