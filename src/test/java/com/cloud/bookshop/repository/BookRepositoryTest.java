package com.cloud.bookshop.repository;

import com.cloud.bookshop.BaseTest;
import com.cloud.bookshop.domain.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookRepositoryTest extends BaseTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testJPAFind() {
        Book book = new Book();
        String bookName = UUID.randomUUID().toString();
        book.setName(bookName);
        bookRepository.save(book);

        List<Book> books = bookRepository.findByName(bookName);
        Assertions.assertTrue(books.size() > 0);

        List<Long> ids = bookRepository.findAll().stream().filter(item -> item.getId() % 2 == 0)
                .map(item -> item.getId()).collect(Collectors.toUnmodifiableList());

        // query all items by ids
        List<Book> bookQuery = bookRepository.findAllById(ids);
        Assertions.assertTrue(ids.size() == bookQuery.size() && bookQuery.size() > 0);
        List<Long> queryIdList = bookQuery.stream().map(item -> item.getId()).collect(Collectors.toUnmodifiableList());
        Assertions.assertEquals(queryIdList, ids);

        // query by providing sort
        List<Book> sortedBooks = bookRepository.findAll(Sort.by(
                Sort.Order.desc("name"),
                Sort.Order.asc("id")
        ));
        sortedBooks.forEach(item -> {
            System.out.println("Book Name: " + item.getName() + ", ID: " + item.getId());
        });
    }

    @Test
    public void testPageableUsage() {
        Pageable pageable = PageRequest.of(0, 3, Sort.by(new Sort.Order(Sort.Direction.DESC, "name")));
        Page<Book> bookPage = bookRepository.findAll(pageable);
        Assertions.assertTrue(bookPage.getTotalElements() > 0);
        Assertions.assertTrue(bookPage.getTotalPages() > 0);
        Assertions.assertTrue(bookPage.getSize() > 0);
        Assertions.assertEquals(bookPage.getSort(), Sort.by(Sort.Order.desc("name")));
        Assertions.assertEquals(bookPage.getNumber(), 0);
    }

    // how to use ExampleMatcher to query database
    @Test
    public void testExampleMatcher() {
        Book b = new Book();
        String bookName = UUID.randomUUID().toString();
        b.setName(bookName);

        // save this record to db
        Book ret = bookRepository.save(b);
        Assertions.assertTrue(ret.getId() > 0L);

        // Create an ExampleMatcher to match all properties of the Book
        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreCase("name");
        Example<Book> bookExample = Example.of(b, matcher);
        List<Book> books = bookRepository.findAll(bookExample);
        Assertions.assertTrue(books.size() > 0);
        Assertions.assertTrue(books.get(0).getName().equals(bookName));
    }
}
