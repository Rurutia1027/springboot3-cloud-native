package com.cloud.bookshop.dubbo.service;

import com.cloud.bookshop.dubbo.dto.BookInfo;

public interface BookService {
    BookInfo getInfo(Long id);
}
