package com.wugui.pojo;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.List;

/**
 * http://meta.omaha.org.cn/range/get?code=CV04.30.002 对应的实体
 *  值域-内容
 *
 * @author: huzekang
 * @Date: 2019-06-01
 */
@Data
@Accessors(chain = true)
@Entity
public class RangeCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* 属性与代码表 - 定义*/
    /**
     * 元数据类型
     */
    private String metadataType;
    /**
     * 值域名称
     */
    private String valueDomainName;
    /**
     * 值域标识符
     */
    private String valueIdentifier;
    /**
     * 定义
     */
    private String definition;
    /**
     * 描述
     */
    private String description;

    /* 代码表 */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)//级联保存、更新、删除、刷新;延迟加载
    @JoinColumn(name = "range_code_id")//在book表增加一个外键列来实现一对多的单向关联
    private List<RangeCodeValue> rangeCodeValueList = Lists.newArrayList();


    /* 关系 - 相关数据元 */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)//级联保存、更新、删除、刷新;延迟加载
    @JoinColumn(name = "range_code_id")//在book表增加一个外键列来实现一对多的单向关联
    private List<RangeCodeRelatedDataElement> rangeCodeRelatedDataElementList =Lists.newArrayList();



    /* 来源 - 标准 */
    /**
     * 标准号
     */
    private String standard;

    /**
     * 标准名称
     */
    private String standardName;

    /**
     * 标准文件
     */
    private String standardFile;


    /**
     * 标准文件下载地址
     */
    private String standardFileDownloadUrl;


    /**
     * 爬取页面url
     */
    private String url;


}
