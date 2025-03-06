package com.cloud.bookshop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;


// This entity is created to store the Book & Author mapping relationships
@Data
@Entity
public class BookAuthor extends DomainImpl {

    @ManyToOne
    private Book book;

    @ManyToOne
    private Author author;
}
