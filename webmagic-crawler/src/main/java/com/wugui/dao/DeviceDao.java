package com.wugui.dao;

import com.wugui.pojo.DeviceEntity;
import com.wugui.pojo.GithubUser;
import org.springframework.data.repository.CrudRepository;

public interface DeviceDao extends CrudRepository<DeviceEntity,Long> {

}
