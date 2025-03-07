package com.cloud.bookshop.domain;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Data;

import java.util.List;


// Category:Book = 1:N
@Entity
@Data
public class Category extends DomainImpl {
    @Basic
    private String name;

    @Transient
    private String fieldShouldNotSave2DB;

    // we add this will let the multiple one to maintain the 1:N relationship
    // via foreign key
    @OneToMany(mappedBy = "category")
    // @OneToMany(mappedBy = "category", orphanRemoval = true)
    // when orphanRemoval=true, if the One side -- the category is removed from db
    // then the associated Book(s) will become orphan(s) then it/they will be removed from db table
    // default value of orphanRemoval is false


    // @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    // CascadeType=Remove means that when category is removed, then it's associated multiple Books will be removed from the DB too.
    private List<Book> bookList;
}
