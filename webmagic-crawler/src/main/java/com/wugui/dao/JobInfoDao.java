package com.wugui.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wugui.pojo.JobInfo;

public interface JobInfoDao extends JpaRepository<JobInfo,Long> {

}
