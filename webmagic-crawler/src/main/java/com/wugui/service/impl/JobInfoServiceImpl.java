package com.wugui.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wugui.dao.JobInfoDao;
import com.wugui.pojo.JobInfo;
import com.wugui.service.JobInfoService;

@Service
public class JobInfoServiceImpl implements JobInfoService {

	@Autowired
	private JobInfoDao jobInfoDao;

	@Override
    @Transactional
	public void save(JobInfo jobInfo) {
		// 先从数据库查询数据,根据发布日期查询和url查询
		JobInfo param = new JobInfo();
        param.setUrl(jobInfo.getUrl());
        param.setTime(jobInfo.getTime());
        List<JobInfo> list = this.findJobInfo(param);

        if (list.size() == 0) {
            //没有查询到数据则新增或者修改数据
            this.jobInfoDao.saveAndFlush(jobInfo); 
        }

	}

	@Override
	public List<JobInfo> findJobInfo(JobInfo jobInfo) {
		Example example = Example.of(jobInfo);
		List<JobInfo> jobs = jobInfoDao.findAll(example);
		return jobs;
	}

}
