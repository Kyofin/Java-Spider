package com.wugui.dao;

import com.wugui.model.Author;
import org.springframework.data.repository.CrudRepository;


public interface AuthorDao extends CrudRepository<Author,Integer> {
}
