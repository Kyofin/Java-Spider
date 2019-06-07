package com.wugui.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 值域-代码表
 */
@Entity
@Data
public class RangeCodeValue {

    @Id
    @GeneratedValue
    private Long id;
    /**
     * 值
     */
    private String value;
    /**
     * 值含义
     */
    private String valueMeaning;
    /**
     * 说明
     */
    private String valueDescription;

    @Column(name = "range_code_id")
    private Long rangeCodeId;

    /**
     * 爬取地址
     */
    private String url;
}