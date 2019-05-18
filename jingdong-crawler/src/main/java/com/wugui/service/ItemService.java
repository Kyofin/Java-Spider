package com.wugui.service;

import java.util.List;

import com.wugui.entity.Item;

public interface ItemService {
    //根据条件查询数据
    public List<Item> findAll(Item item);

    //保存数据
    public void save(Item item);
}
