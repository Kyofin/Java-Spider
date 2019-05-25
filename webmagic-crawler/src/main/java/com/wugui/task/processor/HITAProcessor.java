package com.wugui.task.processor;

import com.wugui.task.HttpClientDownloader;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * HITA 元数据服务爬取
 *
 * 1. 数据集列表页面 参考http://meta.omaha.org.cn/dataSet/?pageNo=1
 *
 *
 * 2. 数据集页面
 *      2.1 没有子集，参考http://meta.omaha.org.cn/dataSet/get?code=HDSB01.03
 *          使用更多按钮进入采集目标页面
 *
 *      2.2 有子集，参考http://meta.omaha.org.cn/dataSet/get?code=HDSD00.14
 *          直接使用子集地址进入采集目标页面
 *
 * 3.采集目标页面数据 参考http://meta.omaha.org.cn/elementOfSetList/get?subset=&dataSetCode=HDSB02.03&pageNo=1
 *
 *
 * @author: huzekang
 * @create: 2019-05-25 10:27
 **/
@Component
public class HITAProcessor implements PageProcessor {


    private Site site = Site.me();


    @Override
    public void process(Page page) {
        // 获取总条数
        String totalCount = page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/div/span/tidyText()").regex("\\d+").get();
        // 获取总页数
        int totalPage = Integer.valueOf(totalCount) / 10 + 1;
        // 将每页地址放入爬取队列中
        for (Integer i = 1; i <=totalPage ; i++) {
            // 拼装所有数据元分页地址
            String url = page.getUrl().replace("pageNo=\\d+", "pageNo=").toString().concat(String.valueOf(i));
            page.addTargetRequest(url);
        }
        // 解析一页中数据集全部数据元 参考：http://meta.omaha.org.cn/elementOfSetList/get?subset=&dataSetCode=HDSB02.03&pageNo=1
        List<String> tableCellStrings = page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr/td/tidyText()").all();
        for (int i = 0; i < tableCellStrings.size(); i++) {

            switch (i % 7) {
                case 0:
                    System.out.println("内部标识符:" + tableCellStrings.get(i));
                    break;
                case 1:
                    System.out.println("数据元名称:" + tableCellStrings.get(i));
                    break;
                case 2:
                    System.out.println("数据元标识符:" + tableCellStrings.get(i));
                    break;
                case 3:
                    System.out.println("定义:" + tableCellStrings.get(i));
                    break;
                case 4:
                    System.out.println("数据类型:" + tableCellStrings.get(i));
                    break;
                case 5:
                    System.out.println("表示格式:" + tableCellStrings.get(i));
                    break;
                case 6:
                    System.out.println("数据元允许值:" + tableCellStrings.get(i));
                    break;
                default:


            }
        }

    }

    @Override
    public Site getSite() {
        return site
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
    }


    public void start() {
        Spider.create(new HITAProcessor())
                // 设置download解决官方webMagic无法访问部分ssl网站问题
                .setDownloader(new HttpClientDownloader())
                .addUrl("http://meta.omaha.org.cn/elementOfSetList/get?subset=&dataSetCode=HDSB02.03&pageNo=1")
//                .setScheduler(new RedisScheduler(new JedisPool("127.0.0.1",6379)))
//                .thread(8)
                .run();

    }
}
