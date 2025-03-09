package com.cloud.web.controller;


import com.cloud.dto.BookCondition;
import com.cloud.dto.BookInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public List<BookInfo> query(BookCondition condition, @PageableDefault(size = 10) Pageable pageable) {
        System.out.println("condition name " + condition.getName());
        System.out.println("condition categoryId " + condition.getCategoryId());

        // current page number
        System.out.println(pageable.getPageNumber());

        // how many pages in total ?
        System.out.println(pageable.getPageSize());

        // get sort info
        System.out.println(pageable.getSort());
        return List.of(new BookInfo(), new BookInfo(), new BookInfo());
    }
}
