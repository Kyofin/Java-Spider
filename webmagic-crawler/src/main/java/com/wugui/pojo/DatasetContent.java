package com.wugui.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * http://meta.omaha.org.cn/dataSet/get?code=HDSA00.01 对应实体
 *
 * @author: huzekang
 * @Date: 2019-06-01
 */
@Entity
@Data
@Accessors(chain = true)
public class DatasetContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* 元数据属性 - 标识信息*/

    /**
     * 	元数据类型
     */
    private String metadataType;

    /**
     * 	数据集名称
     */
    private String datasetName;

    /**
     * 	数据集标识符，可用于关联数据集
     */
    private String datasetIdentifier;

    /**
     * 数据集发布方-单位名称
     */
    private String datasetPublisher;

    /**
     * 	关键词
     */
    private String keywords;

    /**
     * 	数据集语种
     */
    private String datasetLanguage;

    /**
     * 	数据集分类-类目名称
     */
    private String datasetCategory;


    /* 元数据属性 - 内容信息     */

    /**
     * 	数据集摘要
     */
    private String datasetSummary;

    /**
     * 	数据集特征数据元
     */
    private String datasetFeatureDataElement;

    /* 数据元属性 - 公用属性     */

    /**
     * 版本
     */
    private String version;

    /**
     * 注册机构
     */
    private String registrationAuthority;

    /**
     * 相关环境
     */
    private String relatedEnvironment;

    /**
     * 分类模式
     */
    private String classificationMode;

    /**
     * 主管机构
     */
    private String competentAuthority;

    /**
     * 	注册状态
     */
    private String registrationStatus;

    /**
     * 	提交机构
     */
    private String submittedBy;

    /* 来源     */

    /**
     * 	标准号
     */
    private String standard;

    /**
     * 	标准名称
     */
    private String standardName;

    /**
     * 	标准文件
     */
    private String standardFile;

    /**
     * 标准文件下载地址
     */
    private String standardFileDownloadUrl;

    /**
     * 爬取的页面url
     */
    private String url;


}
