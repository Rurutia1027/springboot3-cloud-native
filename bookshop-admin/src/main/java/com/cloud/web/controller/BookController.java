package com.cloud.web.controller;


import com.cloud.dto.BookCondition;
import com.cloud.dto.BookInfo;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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
        bookInfo.setPublishDate(new Date());
        return bookInfo;
    }

    // BindingResult contains all the validate operations final results
    // if any invalid checking detects those invalid results will be sync to result:BindingResult
    @PostMapping
    public BookInfo create(@Valid @RequestBody BookInfo info, BindingResult result) {
        if (result.hasErrors()) {
            result.getAllErrors()
                    .stream().forEach(item -> {
                        System.out.println(item.getDefaultMessage());
                    });
        }

        System.out.println("recv info :" + info.toString());
        info.setId(1L);
        return info;
    }
}
