package com.cloud.bookshop.data.impl;

import com.cloud.bookshop.data.domain.Book;
import com.cloud.bookshop.data.repository.BookRepository;
import com.cloud.bookshop.dubbo.dto.BookCondition;
import com.cloud.bookshop.dubbo.dto.BookInfo;
import com.cloud.bookshop.dubbo.service.BookService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@DubboService
@Service("bookService")
@Transactional
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

    @Override
    public Page<BookInfo> query(BookCondition condition, Pageable pageable) {
        return null;
    }

    @Override
    public BookInfo create(BookInfo info) {
        Book book = new Book();
        book.setName(info.getName());
        book.setCreateTime(new Date());
        book.setVersion(2);
        Book ret = bookRepository.save(book);
        BookInfo retInfo = new BookInfo();
        BeanUtils.copyProperties(ret, retInfo);
        return retInfo;
    }

    @Override
    public BookInfo update(BookInfo info) {
        Book book = bookRepository.findById(info.getId()).get();
        book.setName(info.getName());
        bookRepository.save(book);
        info.setId(book.getId());
        return info;
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }
}
