package com.wugui.task.pipeline;

import com.wugui.dao.GithubUserDao;
import com.wugui.pojo.GithubUser;
import com.wugui.utils.SpringUtil;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class GithubUserJPAPipeline implements Pipeline {


	
	@Override
	public void process(ResultItems resultItems, Task task) {
		GithubUserDao githubUserDao = SpringUtil.getBean(GithubUserDao.class);

		GithubUser githubUser = resultItems.get("githubUser");
		
		if(githubUser != null) {
			githubUserDao.save(githubUser);
		}
	}

}
