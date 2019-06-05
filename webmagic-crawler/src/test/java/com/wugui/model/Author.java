package com.wugui.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 *  作者
 */
@Data
@Entity
@Table(name="author")
public class Author {
    
    @Id
    @GeneratedValue
    private Long id;
    
    /**作者的名字*/
    @Column(length=32)
    private String name;
    
    /**作者写的书*/
    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)//级联保存、更新、删除、刷新;延迟加载
    @JoinColumn(name="author_id")//在book表增加一个外键列来实现一对多的单向关联
    private Set<Book> books = new HashSet<>();


    
    
}