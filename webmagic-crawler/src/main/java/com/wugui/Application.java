package com.wugui;

import com.wugui.task.processor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author huzekang
 */
@SpringBootApplication
@EnableScheduling
public class Application implements CommandLineRunner {

	@Autowired
	GithubRepoProcessor githubRepoProcessor;

	@Autowired
	CsdnBlogPageProcessor csdnBlogPageProcessor;

	@Autowired
	JobProcessor jobProcessor;

	@Autowired
    HITADatasetProcessor hitaDatasetProcessor;

    @Autowired
    HITARangeCodeProcessor hitaRangeCodeProcessor;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		githubRepoProcessor.start();
//		jobProcessor.start();
//		csdnBlogPageProcessor.start();
//		hitaDatasetProcessor.start();
//        hitaRangeCodeProcessor.start();
	}


}
