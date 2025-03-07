package com.cloud.bookshop.repository;

import com.cloud.bookshop.domain.Book;
import com.cloud.bookshop.domain.PrintBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PrintBookRepository extends JpaRepository<PrintBook, Long>, JpaSpecificationExecutor<Book> {
}
