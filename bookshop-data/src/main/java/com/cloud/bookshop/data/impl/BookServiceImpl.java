package com.cloud.bookshop.data.impl;

import com.cloud.bookshop.data.aspect.ServiceLog;
import com.cloud.bookshop.data.domain.Book;
import com.cloud.bookshop.data.repository.BookRepository;
import com.cloud.bookshop.dubbo.dto.BookCondition;
import com.cloud.bookshop.dubbo.dto.BookInfo;
import com.cloud.bookshop.dubbo.service.BookService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;

@DubboService
@Service("bookService")
@Transactional
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;

    // another way to use cache
    @Autowired
    private CacheManager cacheManager;

    @ServiceLog
    @Override
    @Cacheable(value = "books")
    public BookInfo getInfo(Long id) {
        Cache.ValueWrapper value = cacheManager.getCache("books").get(id);
        if (Objects.isNull(value)) {
            Book book = bookRepository.findById(id).get();
            BookInfo bookRet = new BookInfo();
            // cache k,v pair to the cache with name of books
            // which is managed via cache manager
            BeanUtils.copyProperties(book, bookRet);
            cacheManager.getCache("books").put(id, bookRet);
        }

        return (BookInfo) value.get();
    }

    @Override
    //@Cacheable(cacheNames = "books", key = "#condition.name")
    @CacheEvict(cacheNames = "books", allEntries = true, beforeInvocation = false)
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
