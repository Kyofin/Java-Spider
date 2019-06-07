package com.wugui.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * 值域-相关数据元
 */
@Data
@Entity
public class RangeCodeRelatedDataElement {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 引用该值域的数据元代码
     */
    private String relatedDataElementCode;
    /**
     * 引用该值域的数据元名称
     */
    private String relatedDataElementName;
    /**
     * 引用该值域的数据元地址
     */
    private String relatedDataElementUrl;

    @Column(name = "range_code_id")
    private Long rangeCodeId;

    /**
     * 爬取地址
     */
    private String url;

}