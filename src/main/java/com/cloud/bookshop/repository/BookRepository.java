package com.cloud.bookshop.repository;

import com.cloud.bookshop.domain.Book;
import com.cloud.bookshop.support.BookShopRepository;
import org.springframework.data.jpa.repository.EntityGraph;

public interface BookRepository extends BookShopRepository<Book> {
    @EntityGraph(value = "Book.fetch.category.and.authors")
    Book findByName(String name);
}
