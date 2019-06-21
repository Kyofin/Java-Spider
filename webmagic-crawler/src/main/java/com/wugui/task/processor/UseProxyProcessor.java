package com.wugui.task.processor;

import com.wugui.task.HttpClientDownloader;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

/**
 * 结合Webmagic和ssr，使用代理访问谷歌
 * ssr设置使用PAC模式或者手动模式或者ACL自动模式，不要选全局。
 * 图：![](https://i.loli.net/2019/06/22/5d0d12226382066577.png)
 **/
public class UseProxyProcessor implements PageProcessor {
    @Override
    public void process(Page page) {
        System.out.println(page);
    }

    @Override
    public Site getSite() {
        return Site.me();
    }

    public static void main(String[] args) {
        UseProxyProcessor useProxyProcessor = new UseProxyProcessor();
        Proxy proxy = new Proxy("192.168.5.28", 1087);

        // 设置download解决官方webMagic无法访问部分ssl网站问题
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        // 设置代理
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(proxy));
        // 启动爬虫
        Spider.create(useProxyProcessor)
                .setDownloader(httpClientDownloader)
                .addUrl("http://www.google.com")
                .run();

    }
}
