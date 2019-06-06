package com.wugui.task.processor;

import cn.hutool.core.util.ReUtil;
import com.google.common.collect.Lists;
import com.wugui.pojo.*;
import com.wugui.task.HttpClientDownloader;
import com.wugui.task.pipeline.HITADatasetJPAPipeline;
import com.wugui.task.pipeline.HITARangeCodeJPAPipeline;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * HITA 元数据服务 - 值域代码爬取
 *
 * 分类首页 -> 指定分类的分页列表 -> 指定值域代码的内容
 *
 * @author: huzekang
 * @create: 2019-05-25 10:27
 **/
@Component
public class HITARangeCodeProcessor implements PageProcessor {

    private static final String RANGE_CODE_CATEGORY_URL = "http://meta.omaha.org.cn/range/code";

    private static final String RANGE_CODE_CATEGORY_PAGE_URL = "http://meta.omaha.org.cn/range/?catalogCode=";

    private static final String RANGE_CODE_CONTENT_URL = "http://meta.omaha.org.cn/range/get?code=";
    public static final String RANGE_CODE_CONTENT = "rangeCodeContent";

    private Site site = Site.me();


    @Override
    public void process(Page page) {
        String currentUrl = page.getUrl().toString();

        if (currentUrl.contains(RANGE_CODE_CATEGORY_URL)) {

        } else if (currentUrl.contains(RANGE_CODE_CATEGORY_PAGE_URL)) {

        } else if (currentUrl.contains(RANGE_CODE_CONTENT_URL)) {

            // 属性与代码表 - 定义
            RangeCodeContent rangeCodeContent = new RangeCodeContent();
            rangeCodeContent.setMetadataType(page.getHtml().xpath("/html/body/div[@class='main-content']/div[@class='container data-detail']/div[@class='data-detail-content']/table[@class='table-style']/tbody/tr[3]/td/table[@class='table-style']/tbody/tr[1]/td[3]/text()").toString());
            rangeCodeContent.setValueDomainName(page.getHtml().xpath("/html/body/div[@class='main-content']/div[@class='container data-detail']/div[@class='data-detail-content']/table[@class='table-style']/tbody/tr[3]/td/table[@class='table-style']/tbody/tr[2]/td[3]/text()").toString());
            rangeCodeContent.setValueIdentifier(page.getHtml().xpath("/html/body/div[@class='main-content']/div[@class='container data-detail']/div[@class='data-detail-content']/table[@class='table-style']/tbody/tr[3]/td/table[@class='table-style']/tbody/tr[3]/td[3]/text()").toString());
            rangeCodeContent.setDefinition(page.getHtml().xpath("/html/body/div[@class='main-content']/div[@class='container data-detail']/div[@class='data-detail-content']/table[@class='table-style']/tbody/tr[3]/td/table[@class='table-style']/tbody/tr[4]/td[3]/text()").toString());
            rangeCodeContent.setDescription(page.getHtml().xpath("/html/body/div[@class='main-content']/div[@class='container data-detail']/div[@class='data-detail-content']/table[@class='table-style']/tbody/tr[3]/td/table[@class='table-style']/tbody/tr[5]/td[3]/text()").toString());

            // 来源 - 标准
            rangeCodeContent.setStandard(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[11]/td/table/tbody/tr[1]/td[3]/text()").get());
            rangeCodeContent.setStandardName(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[11]/td/table/tbody/tr[2]/td[3]/text()").get());
            rangeCodeContent.setStandardFile(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[11]/td/table/tbody/tr[3]/td[3]/text()").get());
            rangeCodeContent.setStandardFileDownloadUrl(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/ul/li/a").links().get());

            // 爬取的url
            rangeCodeContent.setUrl(currentUrl);

            // 代码表
            List<String> valueTrs = page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[5]/td/table/tbody/tr").all();
            int valueCount = valueTrs.size();
            List<RangeCodeValue> rangeCodeValueList = IntStream.range(1, valueCount).mapToObj(i -> {
                String valueTr = valueTrs.get(i);
                RangeCodeValue rangeCodeValue = new RangeCodeValue();
                rangeCodeValue.setValue(ReUtil.getGroup1("<td width=\"20%\">([\\u4e00-\\u9fa5_a-zA-Z0-9]+)</td>",valueTr));
                rangeCodeValue.setValueMeaning(ReUtil.getGroup1("<td width=\"35%\">([\\u4e00-\\u9fa5_a-zA-Z0-9]+)</td>",valueTr));
                rangeCodeValue.setValueDescription(ReUtil.getGroup1("<td width=\"30%\">([\\u4e00-\\u9fa5_a-zA-Z0-9]+)</td>",valueTr));
                return rangeCodeValue;
            }).collect(Collectors.toList());

            rangeCodeContent.setRangeCodeValueList(rangeCodeValueList);

            //关系 - 相关数据元
            List<String> relatedTrs = page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[8]/td/table/tbody/tr/td[3]/li").all();
            List<String> links = page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[8]/td/table/tbody/tr/td[3]/li").links().all();
            List<RangeCodeRelatedDataElement> rangeCodeRelatedDataElementList = IntStream.range(0, relatedTrs.size()).mapToObj(i -> {
                String relatedTr = relatedTrs.get(i);
                RangeCodeRelatedDataElement rangeCodeRelatedDataElement = new RangeCodeRelatedDataElement();
                rangeCodeRelatedDataElement.setRelatedDataElementName(ReUtil.getGroup1(">([\\u4e00-\\u9fa5_a-zA-Z0-9]+)</a>",relatedTr));
                rangeCodeRelatedDataElement.setRelatedDataElementUrl(links.get(i));
                rangeCodeRelatedDataElement.setRelatedDataElementCode(ReUtil.getGroup1("code=(.+)\">",relatedTr));
                return rangeCodeRelatedDataElement;
            }).collect(Collectors.toList());

            rangeCodeContent.setRangeCodeRelatedDataElementList(rangeCodeRelatedDataElementList);

            // 入库
            page.putField(RANGE_CODE_CONTENT,rangeCodeContent);

        }

    }



    private Integer getTotalPageCount(Page page) {
        // 获取总条数
        String totalCount = page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/div/span/tidyText()").regex("\\d+").get();
        // 获取总页数
        int totalPage = Integer.valueOf(totalCount) / 10 + 1;

        return totalPage;
    }



    @Override
    public Site getSite() {
        return site
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
    }


    public void start() {
        Spider.create(new HITARangeCodeProcessor())
                // 设置download解决官方webMagic无法访问部分ssl网站问题
                .setDownloader(new HttpClientDownloader())
                .setPipelines(Lists.newArrayList(new HITARangeCodeJPAPipeline(),new ConsolePipeline()))
                .addUrl("http://meta.omaha.org.cn/range/get?code=CV05.10.006")
                .thread(1)
                .run();

    }
}
