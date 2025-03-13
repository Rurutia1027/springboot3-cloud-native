package com.cloud.bookshop.service;

import com.cloud.bookshop.dto.BookInfo;

public interface BookService {
    BookInfo getInfo(Long id);
}
