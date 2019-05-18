package com.wugui.dao;

import com.wugui.pojo.GithubRepo;
import com.wugui.pojo.JobInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GithubRepoDao extends CrudRepository<GithubRepo,Long> {

}
