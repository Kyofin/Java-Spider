package com.wugui.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 书
 */
@Entity
@Table(name="book")
@Data
public class Book {
    
    @Id
    @GeneratedValue
    private Long id;
    
    /**书名*/
    @Column(length=32)
    private String name;


    
}