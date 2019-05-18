package com.wugui.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wugui.entity.Item;

public interface ItemDao extends JpaRepository<Item,Long> {
}

