package com.wugui.task.processor;

import com.google.common.collect.Lists;
import com.wugui.pojo.Dataset;
import com.wugui.pojo.DatasetContent;
import com.wugui.pojo.DatasetContentElement;
import com.wugui.task.HttpClientDownloader;
import com.wugui.task.pipeline.HITADatasetJPAPipeline;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * HITA 元数据服务 - 数据集爬取
 *
 * 1. 数据集列表页面 参考http://meta.omaha.org.cn/dataSet/?pageNo=1
 *
 *
 * 2. 数据集内容页面
 *      2.1 没有子集，参考http://meta.omaha.org.cn/dataSet/get?code=HDSB01.03
 *          使用更多按钮进入数据集全部数据元页面
 *
 *      2.2 有子集，参考http://meta.omaha.org.cn/dataSet/get?code=HDSD00.14
 *          直接使用子集地址进入数据集全部数据元页面
 *
 * 3. 数据集全部数据元页面 参考http://meta.omaha.org.cn/elementOfSetList/get?subset=&dataSetCode=HDSB02.03&pageNo=1
 *
 *
 * @author: huzekang
 * @create: 2019-05-25 10:27
 **/
@Component
public class HITADatasetProcessor implements PageProcessor {


    public static final String DATA_SET_PAGE_URL = "http://meta.omaha.org.cn/dataSet";
    public static final String DATA_SET_CONTENT_URL = "http://meta.omaha.org.cn/dataSet/get";
    public static final String DATA_SET_CONTENT_ELEMENT_URL = "http://meta.omaha.org.cn/elementOfSetList/get";

    public static final String KEY_DATASET_LIST = "datasetList";
    public static final String KEY_DATASET_CONTENT = "datasetContent";
    public static final String KEY_DATASET_CONTENT_ELEMENT_LIST = "datasetContentElementList";

    private Site site = Site.me();


    @Override
    public void process(Page page) {
        String currentUrl = page.getUrl().toString();

        if (currentUrl.contains(DATA_SET_CONTENT_ELEMENT_URL)) {
            crawlDatasetContentElements(page);

        } else if (currentUrl.startsWith(DATA_SET_CONTENT_URL)) {
            crawlDatasetContent(page);
        } else if (currentUrl.contains(DATA_SET_PAGE_URL)) {
            crawlDataset(page);
        }

    }

    private void crawlDatasetContent(Page page) {
        DatasetContent datasetContent = new DatasetContent();
        datasetContent
                .setUrl(page.getUrl().get())
                .setMetadataType(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[3]/td/table/tbody/tr[1]/td[3]/text()").get())
                .setDatasetName(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[3]/td/table/tbody/tr[2]/td[3]/text()").get())
                .setDatasetIdentifier(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[3]/td/table/tbody/tr[3]/td[3]/text()").get())
                .setDatasetPublisher(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[3]/td/table/tbody/tr[4]/td[3]/text()").get())
                .setKeywords(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[3]/td/table/tbody/tr[5]/td[3]/text()").get())
                .setDatasetLanguage(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[3]/td/table/tbody/tr[6]/td[3]/text()").get())
                .setDatasetCategory(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[3]/td/table/tbody/tr[7]/td[3]/text()").get())
                .setDatasetSummary(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[5]/td/table/tbody/tr[1]/td[3]/text()").get())
                .setDatasetFeatureDataElement(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[5]/td/table/tbody/tr[2]/td[3]/text()").get())
                .setVersion(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[8]/td/table/tbody/tr[1]/td[3]/text()").get())
                .setRegistrationAuthority(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[8]/td/table/tbody/tr[2]/td[3]/text()").get())
                .setRelatedEnvironment(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[8]/td/table/tbody/tr[3]/td[3]/text()").get())
                .setClassificationMode(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[8]/td/table/tbody/tr[4]/td[3]/text()").get())
                .setCompetentAuthority(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[8]/td/table/tbody/tr[5]/td[3]/text()").get())
                .setRegistrationStatus(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[8]/td/table/tbody/tr[6]/td[3]/text()").get())
                .setSubmittedBy(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[8]/td/table/tbody/tr[7]/td[3]/text()").get())
                .setStandard(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[13]/td/table/tbody/tr[1]/td[3]/text()").get())
                .setStandardName(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[13]/td/table/tbody/tr[2]/td[3]/text()").get())
                .setStandardFile(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[13]/td/table/tbody/tr[3]/td[3]/text()").get())
                .setStandardFileDownloadUrl(page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/ul/li/a").links().get());

        // 将datasetContent放入pipeline，之后入库
        page.putField(KEY_DATASET_CONTENT, datasetContent);

        // 判断是否有子集
        List<String> subSetUrls = page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr[10]/td/table/tbody/tr/td[3]/li/a").links().all().stream()
                .filter(Objects::nonNull)
                .map(e -> e + "&pageNo=1")
                .collect(Collectors.toList());
        if (subSetUrls.size() > 0) {
            // 如果有则将它们的地址放入爬取队列。
            page.addTargetRequests(subSetUrls);
        } else {
            //没有将该数据集的数据元地址放入爬取队列
            String dataSetCode = page.getUrl().regex("code=(\\w+.\\w+)").get();
            final String elementUrl = "http://meta.omaha.org.cn/elementOfSetList/get?subset=&dataSetCode=" + dataSetCode + "&pageNo=1";
            page.addTargetRequest(elementUrl);
        }


    }

    private void crawlDataset(Page page) {
        List<String> datasetContentLinks = page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr/td/a").links().all();
        List<String> datasetNames = page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr/td/a/text()").all();
        List<String> datasetNumbers = page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr/td[3]/tidyText()").all();
        List<String> datasetCategories = page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr/td[4]/tidyText()").all();

        // 将一页的全部数据集放入pipeline
        List<Dataset> datasetResultList = IntStream.range(0, datasetNames.size()).mapToObj(i -> new Dataset(null, datasetNames.get(i), datasetNumbers.get(i), datasetCategories.get(i), datasetContentLinks.get(i), page.getUrl().get())).collect(Collectors.toList());
        page.putField(KEY_DATASET_LIST, datasetResultList);

        // 将数据集内容地址放入爬取队列
        page.addTargetRequests(datasetContentLinks);

        // 获取总页数
        int totalPage = getTotalPageCount(page);
        // 将每页地址放入爬取队列中
        for (Integer i = 1; i <= totalPage; i++) {
            // 拼装所有数据集分页地址
            String pageUrl = page.getUrl().replace("pageNo=\\d+", "pageNo=").toString().concat(String.valueOf(i));
            // 将分页地址放入爬取队列
            page.addTargetRequest(pageUrl);
        }
    }

    private Integer getTotalPageCount(Page page) {
        // 获取总条数
        String totalCount = page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/div/span/tidyText()").regex("\\d+").get();
        // 获取总页数
        int totalPage = Integer.valueOf(totalCount) / 10 + 1;

        return totalPage;
    }

    /**
     * 爬一个数据集里所有数据元分页的内容
     *
     * @author: huzekang
     * @Date: 2019-05-31
     */
    private void crawlDatasetContentElements(Page page) {
        /* 分页处理**/
        // 获取总页数
        int totalPage = getTotalPageCount(page);
        // 将每页地址放入爬取队列中
        for (Integer i = 1; i <= totalPage; i++) {
            // 拼装所有数据元分页地址
            String url = page.getUrl().replace("pageNo=\\d+", "pageNo=").toString().concat(String.valueOf(i));
            page.addTargetRequest(url);
        }

        /* 页面内容处理**/
        // 解析一页中数据集全部数据元 参考：http://meta.omaha.org.cn/elementOfSetList/get?subset=&dataSetCode=HDSB02.03&pageNo=1
        List<String> tableCellStrings = page.getHtml().xpath("/html/body/div[2]/div[1]/div[2]/table/tbody/tr/td/tidyText()").all();

        List<DatasetContentElement> datasetContentElementList = Lists.newArrayList();
        DatasetContentElement datasetContentElement = null;
        for (int i = 0; i < tableCellStrings.size(); i++) {
            switch (i % 7) {
                case 0:
                    // 内部标识符
                    datasetContentElement = new DatasetContentElement();
                    datasetContentElement.setInternalIdentifier(tableCellStrings.get(i));
                    break;
                case 1:
                    // 数据元名称
                    datasetContentElement.setDataElementName(tableCellStrings.get(i));
                    break;
                case 2:
                    // 数据元标识符
                    datasetContentElement.setDataElementIdentifier(tableCellStrings.get(i));
                    break;
                case 3:
                    // 定义
                    datasetContentElement.setDefinition(tableCellStrings.get(i));
                    break;
                case 4:
                    // 数据类型
                    datasetContentElement.setDataType(tableCellStrings.get(i));
                    break;
                case 5:
                    // 表示格式
                    datasetContentElement.setPresentationFormat(tableCellStrings.get(i));
                    break;
                case 6:
                    // 数据元允许值
                    datasetContentElement.setDataElementAllowedValue(tableCellStrings.get(i));
                    // 该数据元的关联数据集code
                    datasetContentElement.setDatasetIdentifier(page.getUrl().regex("dataSetCode=(\\w+.\\w+)").get());

                    datasetContentElement.setUrl(page.getUrl().get());
                    datasetContentElementList.add(datasetContentElement);
                    break;
                default:

            }

        }
        // 将该页全部数据元放入pipeline，后续入库
        if (CollectionUtils.isNotEmpty(datasetContentElementList)) {
            page.putField(KEY_DATASET_CONTENT_ELEMENT_LIST, datasetContentElementList);
        }
    }

    @Override
    public Site getSite() {
        return site
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.131 Safari/537.36");
    }


    public void start() {
        Spider.create(new HITADatasetProcessor())
                // 设置download解决官方webMagic无法访问部分ssl网站问题
                .setDownloader(new HttpClientDownloader())
                .setPipelines(Lists.newArrayList(new HITADatasetJPAPipeline()))
                .addUrl("http://meta.omaha.org.cn/dataSet/?pageNo=1")
//                .addUrl("http://meta.omaha.org.cn/dataSet/get?code=HDSD00.14")
//                .addUrl("http://meta.omaha.org.cn/dataSet/get?code=HDSB01.03")
//                .addUrl("http://meta.omaha.org.cn/elementOfSetList/get?subset=&dataSetCode=HDSB01.04&pageNo=2")
//                .addUrl("http://meta.omaha.org.cn/elementOfSetList/get?subset=&dataSetCode=HDSB02.03&pageNo=1")
//                .setScheduler(new RedisScheduler(new JedisPool("127.0.0.1",6379)))
                .thread(9)
                .runAsync();

    }
}
