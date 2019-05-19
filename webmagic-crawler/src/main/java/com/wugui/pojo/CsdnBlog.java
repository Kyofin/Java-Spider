package com.wugui.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@Accessors(chain = true)
public class CsdnBlog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int keyId;

	private int id;// 编号

	private String title;// 标题

	private String date;// 日期

	private String tags;// 标签

	private String category;// 分类

	private int view;// 阅读人数

	private int comments;// 评论人数

	private int copyright;// 是否原创


}
