package com.wugui.dao;

import com.wugui.pojo.GithubRepo;
import com.wugui.pojo.GithubUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface GithubUserDao extends CrudRepository<GithubUser,Long> {

}
