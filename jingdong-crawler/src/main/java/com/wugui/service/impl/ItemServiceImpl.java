package com.wugui.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wugui.dao.ItemDao;
import com.wugui.entity.Item;
import com.wugui.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemDao itemDao;

	@Override
	public List<Item> findAll(Item item) {
		Example example = Example.of(item);
		List list = this.itemDao.findAll(example);
		return list;
	}

	@Override
	@Transactional
	public void save(Item item) {
		this.itemDao.save(item);
	}

}
