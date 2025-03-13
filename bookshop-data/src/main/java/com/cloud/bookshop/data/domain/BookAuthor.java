package com.cloud.bookshop.data.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// This entity is created to store the Book & Author mapping relationships
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookAuthor extends DomainImpl {

    @ManyToOne
    private Book book;

    @ManyToOne
    private Author author;
}
