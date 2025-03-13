package com.cloud.bookshop.data.repository;

import com.cloud.bookshop.data.domain.Book;
import com.cloud.bookshop.data.support.BookShopRepository;
import org.springframework.data.jpa.repository.EntityGraph;

public interface BookRepository extends BookShopRepository<Book> {
    @EntityGraph(value = "Book.fetch.category.and.authors")
    Book findByName(String name);
}
