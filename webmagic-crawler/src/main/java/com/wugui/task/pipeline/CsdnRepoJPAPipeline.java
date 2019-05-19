package com.wugui.task.pipeline;

import com.wugui.dao.CsdnBlogDao;
import com.wugui.dao.GithubRepoDao;
import com.wugui.pojo.CsdnBlog;
import com.wugui.pojo.GithubRepo;
import com.wugui.utils.SpringUtil;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class CsdnRepoJPAPipeline implements Pipeline {


	
	
	@Override
	public void process(ResultItems resultItems, Task task) {
		CsdnBlogDao csdnBlogDao = SpringUtil.getBean(CsdnBlogDao.class);

		CsdnBlog csdnBlog = resultItems.get("csdnBlog");

		if(csdnBlog != null) {
			csdnBlogDao.save(csdnBlog);
		}
	}

}
