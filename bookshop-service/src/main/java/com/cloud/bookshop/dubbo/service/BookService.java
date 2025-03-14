package com.cloud.bookshop.dubbo.service;

import com.cloud.bookshop.dubbo.dto.BookCondition;
import com.cloud.bookshop.dubbo.dto.BookInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookInfo getInfo(Long id);

    Page<BookInfo> query(BookCondition condition, Pageable pageable);

    BookInfo create(BookInfo info);

    BookInfo update(BookInfo info);

    void delete(Long id);

}
