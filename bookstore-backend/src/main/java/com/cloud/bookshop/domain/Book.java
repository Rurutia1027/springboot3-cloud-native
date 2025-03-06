package com.cloud.bookshop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

// Category:Book = 1:N
@Entity
@Data
public class Book extends DomainImpl {
    private String name;

    // fetch category immediately when book is queried from DB @ManyToOne(fetch = FetchType.EAGER)
    // EAGER is the default option on the ManyToOne side
    // ---
    // fetch category only when user invoke it get function
    // @ManyToOne(fetch = FetchType.LAZY)
    // optional = true, this means we must specify Category of one Book
    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "book")
    private List<BookAuthor> authors;
}
