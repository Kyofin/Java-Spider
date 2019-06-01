package com.wugui.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

/**
*   http://meta.omaha.org.cn/dataSet 对应的实体
*
* @author: huzekang 
* @Date: 2019-05-31 
*/
@Data
@AllArgsConstructor
@Accessors(chain = true)
@Entity
public class Dataset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 数据集名称
     */
    private String datasetName;

    /**
     * 数据集标识符
     */
    private String datasetIdentifier;

    /**
     * 数据集分类-类目名称
     */
    private String datasetCategory;

    /**
     * 该数据集对应内容url
     */
    private String datasetContentUrl;

    /**
     * 爬取到该数据的页面地址
     */
    private String url;

}
