package com.wugui.task.pipeline;

import com.wugui.dao.GithubRepoDao;
import com.wugui.pojo.GithubRepo;
import com.wugui.utils.SpringUtil;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class GithubRepoJPAPipeline implements Pipeline {


	
	
	@Override
	public void process(ResultItems resultItems, Task task) {
		GithubRepoDao githubRepoDao = SpringUtil.getBean(GithubRepoDao.class);

		GithubRepo githubRepo = resultItems.get("githubRepo");

		if(githubRepo != null) {
			githubRepoDao.save(githubRepo);
		}
	}

}
