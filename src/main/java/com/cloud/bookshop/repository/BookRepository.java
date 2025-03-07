package com.cloud.bookshop.repository;

import com.cloud.bookshop.domain.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    @EntityGraph(value = "Book.fetch.category.and.authors")
    Book findByName(String name);
}
