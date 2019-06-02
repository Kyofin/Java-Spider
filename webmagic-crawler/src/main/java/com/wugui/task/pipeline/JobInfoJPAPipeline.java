package com.wugui.task.pipeline;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wugui.pojo.JobInfo;
import com.wugui.service.JobInfoService;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
@Slf4j
public class JobInfoJPAPipeline implements Pipeline {

	@Autowired
	private JobInfoService jobInfoService;
	
	
	@Override
	public void process(ResultItems resultItems, Task task) {
		JobInfo jobInfo = resultItems.get("jobInfo");


		if(jobInfo != null) {
			try {
				this.jobInfoService.save(jobInfo);
			} catch (Exception e) {
				log.error(jobInfo.toString());
			}
		}
	}

}
