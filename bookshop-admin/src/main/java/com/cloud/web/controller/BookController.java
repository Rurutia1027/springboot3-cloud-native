package com.cloud.web.controller;


import com.cloud.dto.BookCondition;
import com.cloud.dto.BookInfo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @GetMapping
    @JsonView(BookInfo.BookListView.class)
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

    @GetMapping("/{id:\\d}")
    @JsonView(BookInfo.BookDetailView.class)
    public BookInfo getInfo(@PathVariable Long id) {
        System.out.println("recv variable id is " + id);

        // create an instance here
        BookInfo bookInfo = new BookInfo();
        bookInfo.setName("mock_book_name");
        bookInfo.setId(233L);
        return bookInfo;
    }
}
