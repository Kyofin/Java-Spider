package com.wugui.dao;

import com.wugui.pojo.Dataset;
import org.springframework.data.repository.CrudRepository;


public interface DatasetDao extends CrudRepository<Dataset,Integer> {
}
