/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.testabcd.authorbooks;

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author thorsten
 */
@Entity
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Author author;

    public Book() {
        // JPA
    }

    private Date date1 = new Date(System.currentTimeMillis());
//    private Date date2 = new Date(System.currentTimeMillis());
//    private Date date3 = new Date(System.currentTimeMillis());
//    private Date date4 = new Date(System.currentTimeMillis());
//    private Date date5 = new Date(System.currentTimeMillis());

    public Book(String name) {
        this.name = name;
    }
}
