package com.cloud.bookshop.service.impl;

import com.cloud.bookshop.domain.Book;
import com.cloud.bookshop.dto.BookInfo;
import com.cloud.bookshop.repository.BookRepository;
import com.cloud.bookshop.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("bookService")
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookInfo getInfo(Long id) {
        Book book = bookRepository.findById(id).get();
        return new BookInfo();
    }
}
