package com.cloud.web.controller;


import com.cloud.dto.BookInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public List<BookInfo> query() {
        return List.of(new BookInfo(), new BookInfo(), new BookInfo());
    }
}
