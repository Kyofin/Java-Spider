package com.wugui.task.processor;

import java.util.List;

import com.wugui.task.MathSalary;
import com.wugui.task.pipeline.JobInfoJPAPipeline;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wugui.pojo.JobInfo;

import redis.clients.jedis.JedisPool;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.RedisScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

/**
* 爬取51job招聘信息
*
* @author: huzekang
* @Date: 2019-05-18
*/
@Component
public class JobProcessor implements PageProcessor {

	private String url = "https://search.51job.com/list/000000,000000,0000,01%252C32,9,99,java,2,1.html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";
	
	@Autowired
	private JobInfoJPAPipeline jobInfoJPAPipeline;

	@Override
	public void process(Page page) {
		// 获取页面数据
		List<Selectable> nodes = page.getHtml().css("div#resultList div.el").nodes();

		if (nodes.size() == 0) {
			try {
				// 如果为空，表示这是招聘信息详情页,保存信息详情
				this.saveJobInfo(page);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			// 如果有值，表示这是招聘信息列表页
			for (Selectable node : nodes) {
				// 获取招聘信息详情页url
				String jobUrl = node.links().toString();
				// 添加到url任务列表中，等待下载
				page.addTargetRequest(jobUrl);
				
/*				 获取翻页按钮的超链接
				List<String> listUrl = page.getHtml().$("div.p_in li.bk").links().all();
				 添加到任务列表中
				page.addTargetRequests(listUrl);*/
			}

            //获取下一页的url
            String bkUrl = page.getHtml().css("div.p_in li.bk").nodes().get(1).links().toString();
            //把url放到任务队列中
            page.addTargetRequest(bkUrl);
		}
	}

	private void saveJobInfo(Page page) {
		// 创建招聘信息对象
		JobInfo jobInfo = new JobInfo();
		Html html = page.getHtml();

		/*// 公司名称
		jobInfo.setCompanyName(html.$("div.tHeader p.cname a", "text").toString());
		// 公司地址
		jobInfo.setCompanyAddr(html.$("div.tBorderTop_box:nth-child(3) p.fp", "text").toString());
		// 公司信息
		jobInfo.setCompanyInfo(html.$("div.tmsg", "text").toString());
		// 职位名称
		jobInfo.setJobName(html.$("div.tHeader > div.in > div.cn > h1", "text").toString());
		// 工作地点
		jobInfo.setJobAddr(html.$("div.tHeader > div.in > div.cn > span.lname", "text").toString());
		// 职位信息
		jobInfo.setJobInfo(Jsoup.parse(html.$("div.tBorderTop_box:nth-child(2)").toString()).text());
		// 工资范围
		String salaryStr = html.$("div.tHeader > div.in > div.cn > strong", "text").toString();
		jobInfo.setSalaryMin(MathSalary.getSalary(salaryStr)[0]);
		jobInfo.setSalaryMax(MathSalary.getSalary(salaryStr)[1]);
		// 职位详情url
		jobInfo.setUrl(page.getUrl().toString());
		// 职位发布时间
		String time = html.$("div.jtag > div.t1 > span.sp4", "text").regex(".*发布").toString();
		jobInfo.setTime(time.substring(0, time.length() - 2));
*/
		
        //获取数据，封装到对象中
        jobInfo.setCompanyName(html.css("div.cn p.cname a","text").toString());
        //jobInfo.setCompanyAddr(Jsoup.parse(html.css("div.tBorderTop_box div.bmsg p.fp:nth-child(2)").toString()).text());
        jobInfo.setCompanyAddr(html.$("div.tBorderTop_box:nth-child(2) p.fp", "text").toString());
        jobInfo.setCompanyInfo(Jsoup.parse(html.css("div.tmsg").toString()).text()) ;
        jobInfo.setJobName(html.css("div.cn h1","text").toString());
        //jobInfo.setJobAddr(html.css("div.tHeader > div.in > div.cn > p.msg","text").toString().substring(0, 3));//上海、南京等
        
/*        <p class="msg ltype" title="杭州-西湖区&nbsp;&nbsp;|&nbsp;&nbsp;3-4年经验&nbsp;&nbsp;|&nbsp;&nbsp;本科&nbsp;&nbsp;|&nbsp;&nbsp;招1人&nbsp;&nbsp;|&nbsp;&nbsp;12-12发布&nbsp;&nbsp;|&nbsp;&nbsp;计算机科学与技术"> 杭州-西湖区&nbsp;&nbsp;<span>|</span>&nbsp;&nbsp;3-4年经验&nbsp;&nbsp;<span>|</span>&nbsp;&nbsp;本科&nbsp;&nbsp;<span>|</span>&nbsp;&nbsp;招1人&nbsp;&nbsp;<span>|</span>&nbsp;&nbsp;12-12发布&nbsp;&nbsp;<span>|</span>&nbsp;&nbsp;计算机科学与技术 </p>
        <p class="msg ltype" title="杭州-西湖区		*/        
        String jobAddr = html.css("div.tHeader > div.in > div.cn > p.msg").toString();
        jobAddr = jobAddr.substring(0, jobAddr.indexOf("&"));
        jobAddr = jobAddr.substring(jobAddr.lastIndexOf("\"")+1);
        System.out.println(jobAddr);
        jobInfo.setJobAddr(jobAddr);
        
        jobInfo.setJobInfo(Jsoup.parse(html.css("div.job_msg").toString()).text());
        jobInfo.setUrl(page.getUrl().toString());

        //获取薪资
        Integer[] salary = MathSalary.getSalary(html.css("div.cn strong", "text").toString());
        if(salary.length>=2) {
	        jobInfo.setSalaryMin(salary[0]);
	        jobInfo.setSalaryMax(salary[1]);
        }else {
	        jobInfo.setSalaryMin(0);
	        jobInfo.setSalaryMax(1);
		}
        //获取发布时间
        String s1 = html.css("div.cn p.msg").toString();
        s1 = s1.substring(s1.lastIndexOf(";")+1);//;12-12发布</p>
        		
        String time = Jsoup.parse(s1).text();
        jobInfo.setTime(time.substring(0,time.length()-2));
        
		// 保存数据
		page.putField("jobInfo", jobInfo);

	}

    private Site site = Site.me()
            .setCharset("gbk")//设置编码
            .setTimeOut(10 * 1000)//设置超时时间
            .setRetrySleepTime(3000)//设置重试的间隔时间
            .setRetryTimes(3);//设置重试的次数
    
	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

	/**
	* 定时爬取最新的数据
	*
	* @author: huzekang
	* @Date: 2019-05-18
	*/
	@Scheduled(initialDelay = 1, fixedDelay = 1000 * 100)
	public void start() {

		Spider.create(new JobProcessor())
				.addUrl(url)
				.setScheduler(new RedisScheduler(new JedisPool("127.0.0.1",6379)))				.thread(5)
				.addPipeline(jobInfoJPAPipeline)
				.thread(8)
				.run();

	}

}
