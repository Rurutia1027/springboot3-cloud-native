package com.cloud.bookshop.data.impl;

import com.cloud.bookshop.data.domain.Book;
import com.cloud.bookshop.data.repository.BookRepository;
import com.cloud.bookshop.dubbo.dto.BookInfo;
import com.cloud.bookshop.dubbo.service.BookService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@DubboService
@Service("bookService")
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    @Override
    public BookInfo getInfo(Long id) {
        Book book = bookRepository.findById(id).get();
        BookInfo bookRet = new BookInfo();
        BeanUtils.copyProperties(book, bookRet);
        return bookRet;
    }
}
