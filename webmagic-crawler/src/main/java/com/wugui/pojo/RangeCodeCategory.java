package com.wugui.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 值域分类
 *
 * @program: java-crawler
 * @author: huzekang
 * @create: 2019-06-06 15:53
 **/
@Data
@Accessors(chain = true)
@Entity
public class RangeCodeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 值域分类名
     */
    private String categoryName;

    /**
     * 值域分类code
     */
    private String categoryCode;

    /**
     * 值域名称
     */
    private String valueDomainName;

    /**
     * 值域标识符
     */
    private String valueIdentifier;

    /**
     * 爬取地址
     */
    private String url;

}
