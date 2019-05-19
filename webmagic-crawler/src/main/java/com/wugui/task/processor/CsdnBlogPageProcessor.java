package com.wugui.task.processor;

import com.google.common.collect.Lists;
import com.wugui.pojo.CsdnBlog;
import com.wugui.task.pipeline.CsdnRepoJPAPipeline;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.PhantomJSDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * CSDN博客爬虫
 * 
 * @describe 可以爬取指定用户的csdn博客所有文章，并保存到数据库中。
 *
 */
@Component
public class CsdnBlogPageProcessor implements PageProcessor {

	private static String username = "qq598535550";// 设置csdn用户名
	private static int size = 0;// 共抓取到的文章数量

	// 抓取网站的相关配置，包括：编码、抓取间隔、重试次数等
	private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

	@Override
	public Site getSite() {
		return site;
	}

	@Override
	// process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
	public void process(Page page) {
		// 列表页
		if (!page.getUrl().regex("http://blog\\.csdn\\.net/" + username + "/article/details/\\d+").match()) {
			// 添加所有文章页
			page.addTargetRequests(page.getHtml().css(".article-list").links()// 限定文章列表获取区域
					.regex("/" + username + "/article/details/\\d+")
					.replace("/" + username + "/", "http://blog.csdn.net/" + username + "/")// 巧用替换给把相对url转换成绝对url
					.all());
			// 添加其他列表页
			page.addTargetRequests(page.getHtml().xpath("//div[@id='papelist']").links()// 限定其他列表页获取区域
					.regex("/" + username + "/article/list/\\d+")
					.replace("/" + username + "/", "http://blog.csdn.net/" + username + "/")// 巧用替换给把相对url转换成绝对url
					.all());

			// 获取分页数
			int pageSize = page.getHtml().xpath("//*[@class=\"pagination-box\"]//ul/li").xpath("/*[@data-page]").all().size();
			// 将分页地址全部都加入爬虫队列，由于去重机制，不会重复
			List<String> pageUrls = IntStream.rangeClosed(1,pageSize).mapToObj(i -> "https://blog.csdn.net/" + username + "/article/list//" + i).collect(Collectors.toList());
			page.addTargetRequests(pageUrls);

			// 文章页
		} else {
			size++;// 文章数量加1
			// 用CsdnBlog类来存抓取到的数据，方便存入数据库
			CsdnBlog csdnBlog = new CsdnBlog();
			// 设置编号
			csdnBlog.setId(Integer.parseInt(
					page.getUrl().regex("http://blog\\.csdn\\.net/" + username + "/article/details/(\\d+)").get()));
			// 设置标题
			csdnBlog.setTitle(
					page.getHtml().css(".title-article").xpath("/tidyText()").get());
			// 设置日期
			csdnBlog.setDate(
					page.getHtml().css(".time").xpath("tidyText()").get());
			// 设置标签（可以有多个，用,来分割）
			csdnBlog.setTags(listToString(page.getHtml()
					.xpath("//div[@class='article_l']/span[@class='link_categories']/a/allText()").all()));
			// 设置类别（可以有多个，用,来分割）
			csdnBlog.setCategory(
					listToString(page.getHtml().xpath("//div[@class='category_r']/label/span/text()").all()));
			// 设置阅读人数
			csdnBlog.setView(Integer.parseInt(page.getHtml().css(".read-count").xpath("tidyText()")
					.regex("阅读数：(\\d+)").get()));
			// 设置评论人数
			csdnBlog.setComments(Integer.parseInt(Optional.ofNullable(page.getHtml().css("#btnMoreComment").regex("登录 查看 (\\d+) 条热评").get()).orElse(String.valueOf(0))));
			// 设置是否原创
			csdnBlog.setCopyright(page.getHtml().regex("article-copyright").match() ? 1 : 0);

			// 把对象交给pipeline存入数据库
			page.putField("csdnBlog",csdnBlog);
		}
	}

	// 把list转换为string，用,分割
	public static String listToString(List<String> stringList) {
		if (stringList == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		boolean flag = false;
		for (String string : stringList) {
			if (flag) {
				result.append(",");
			} else {
				flag = true;
			}
			result.append(string);
		}
		return result.toString();
	}


	public  void start() {
		long startTime, endTime;
		System.out.println("【爬虫开始】请耐心等待一大波数据到你碗里来...");
		startTime = System.currentTimeMillis();
		// 从用户博客首页开始抓，开启5个线程，启动爬虫
		Spider.create(new CsdnBlogPageProcessor())
				.addUrl("http://blog.csdn.net/" + username)
				// 在列表页面，分页div是用js加载出来的，所以采用phantomJs来模拟浏览器获取<div class="pagination-box" id="pageBox">完整的标签
				// 这里需要下载phantomJs到本地，并指定一个运行脚本。可参考该类的方法注释。
				.setDownloader(new PhantomJSDownloader("/Users/huzekang/opt/phantomjs-2.1.1-macosx/bin/phantomjs",
						"/Users/huzekang/opt/phantomjs-2.1.1-macosx/bin/crawl.js"))
				.setPipelines(Lists.newArrayList(new CsdnRepoJPAPipeline()))
				.thread(5)
				.run();
		endTime = System.currentTimeMillis();
		System.out.println("【爬虫结束】共抓取" + size + "篇文章，耗时约" + ((endTime - startTime) / 1000) + "秒，已保存到数据库，请查收！");
	}

}
