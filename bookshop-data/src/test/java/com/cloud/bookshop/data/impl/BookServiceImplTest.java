package com.cloud.bookshop.data.impl;

import com.cloud.bookshop.data.BaseTest;
import com.cloud.bookshop.data.repository.BookRepository;
import com.cloud.bookshop.dubbo.dto.BookInfo;
import com.cloud.bookshop.dubbo.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

class BookServiceImplTest extends BaseTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Test
    void whenCreateSuccess() {
        long count = bookRepository.count();
        BookInfo info = new BookInfo();
        String bName = UUID.randomUUID().toString();
        info.setName(bName);
        BookInfo retInfo = bookService.create(info);
        Assertions.assertEquals(count + 1, bookRepository.count());
        Assertions.assertEquals(bName, bookRepository.findByName(bName).getName());
        Assertions.assertEquals(retInfo.getName(), bName);
    }

    @Test
    public void whenUpdateSuccess() {
        BookInfo info = new BookInfo();
        String bName1 = UUID.randomUUID().toString();
        String bName2 = UUID.randomUUID().toString();

        info.setName(bName1);
        BookInfo createRet = bookService.create(info);
        Long bId = createRet.getId();
        createRet.setName(bName2);
        BookInfo updateRet = bookService.update(createRet);
        Assertions.assertEquals(updateRet.getName(), bName2);
        Assertions.assertEquals(bName2, bookRepository.findById(bId).get().getName());
    }

    @Test
    public void whenDeleteSuccess() {
        BookInfo bookInfo = new BookInfo();
        String bName = UUID.randomUUID().toString();
        bookInfo.setName(bName);
        BookInfo bookRet = bookService.create(bookInfo);
        Assertions.assertTrue(bookRet.getId() > 0);
        bookService.delete(bookRet.getId());
        Assertions.assertTrue(bookRepository.findById(bookRet.getId()).isEmpty());
    }
}