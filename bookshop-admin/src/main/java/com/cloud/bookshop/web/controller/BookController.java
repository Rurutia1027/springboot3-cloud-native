package com.cloud.bookshop.web.controller;


import com.cloud.bookshop.dubbo.dto.BookCondition;
import com.cloud.bookshop.dubbo.dto.BookInfo;
import com.cloud.bookshop.dubbo.service.BookService;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/book")
public class BookController {
    @DubboReference
    private BookService bookService;

    @GetMapping
    @JsonView(BookInfo.BookListView.class)
    @Operation(summary = "Get greeting message", description = "This API returns a greeting message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved greeting message"),
            @ApiResponse(responseCode = "400", description = "Bad Request")
    })
    public List<BookInfo> query(@Parameter(description = "Query BookCondition of the Controller", required = true)
                                BookCondition condition, @PageableDefault(size = 10) Pageable pageable) {

        // get SpringContext and then get Authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication " + authentication);

        if (Objects.nonNull(authentication)) {
            System.out.println("Authentication#principal " + authentication.getPrincipal());
        }


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

    @GetMapping("/item/{id}")
    public BookInfo getBookInfo(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication " + authentication);

        if (Objects.nonNull(authentication)) {
            System.out.println("Authentication#principal " + authentication);
        }

        BookInfo bookInfo = bookService.getInfo(id);
        return bookInfo;
    }


    @GetMapping("/{id}")
    @JsonView(BookInfo.BookDetailView.class)
    public BookInfo getInfo(@PathVariable Long id, @CookieValue String token, @RequestHeader String auth) {
        System.out.println("recv variable id is " + id);
        System.out.println("recv token value is " + token);
        System.out.println("recv header auth value is " + auth);

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

    @PutMapping("/{id}")
    public BookInfo update(@Valid @RequestBody BookInfo info, BindingResult result) {
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

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        System.out.println("gonna delete id " + id);
    }

}
