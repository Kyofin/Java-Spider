package com.wugui;

import com.wugui.task.processor.CsdnBlogPageProcessor;
import com.wugui.task.processor.GithubRepoProcessor;
import com.wugui.task.processor.JobProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Application implements CommandLineRunner {

	@Autowired
	GithubRepoProcessor githubRepoProcessor;

	@Autowired
	CsdnBlogPageProcessor csdnBlogPageProcessor;

	@Autowired
	JobProcessor jobProcessor;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		githubRepoProcessor.start();
//		jobProcessor.start();
		csdnBlogPageProcessor.start();
	}

}
