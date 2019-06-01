package com.wugui.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
* http://meta.omaha.org.cn/elementOfSetList/get?subset=&dataSetCode=HDSB01.03&pageNo=1 对应的实体
*
* @author: huzekang
* @Date: 2019-06-01
*/
@Data
@Accessors(chain = true)
@Entity
public class DatasetContentElement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用于关联对应数据集编码
     */
    private String datasetIdentifier;


    /**
     * 内部标识符
     */
    private String internalIdentifier;

    /**
     * 数据元名称
     */
    private String dataElementName;

    /**
     * 数据元标识符
     */
    private String dataElementIdentifier;

    /**
     * 定义
     */
    private String definition;

    /**
     * 数据类型
     */
    private String dataType;

    /**
     * 表示格式
     */
    private String presentationFormat;


    /**
     * 数据元允许值，此值用于关联值域代码，参考 http://meta.omaha.org.cn/range/get?code=CV02.01.101
     */
    private String dataElementAllowedValue;


    /**
     * 爬取页面url
     */
    private String url;
}
