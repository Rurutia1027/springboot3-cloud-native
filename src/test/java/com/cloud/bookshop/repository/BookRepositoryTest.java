package com.cloud.bookshop.repository;

import com.cloud.bookshop.BaseTest;
import com.cloud.bookshop.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class BookRepositoryTest extends BaseTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testFindByName() {
        Book book = new Book();
        String bookName = UUID.randomUUID().toString();
        book.setName(bookName);
        bookRepository.save(book);
        List<Book> books = bookRepository.findByName(bookName);
        System.out.println(books.size());

        books = bookRepository.findByName(bookName);
        System.out.println("retBook " + books.size());

        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        Iterable<Book> iter = bookRepository.findAllById(ids);
        Iterator<Book> iterator = iter.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getName());
        }

        List<Book> all = bookRepository.findAll(Sort.by(new Sort.Order(Sort.Direction.DESC, "name"), new Sort.Order(Sort.Direction.DESC, "id")));
        for (Book b : all) {
            System.out.println(b.getName() + " id: " + b.getId());
        }
    }

    @Test
    public void testPageable() {
        Pageable pageable = PageRequest.of(0, 3, Sort.by(new Sort.Order(Sort.Direction.DESC, "name")));
        Page<Book> bookPage = bookRepository.findAll(pageable);

        System.out.println(bookPage.getTotalElements());
        System.out.println(bookPage.getTotalPages());
        System.out.println(bookPage.getSize());
        System.out.println(bookPage.getSort());
        System.out.println(bookPage.getNumber());
    }

    @Test
    public void test1 () {
        Book b  =new Book();
        Example<Book> exmp = Example.of(b);

    }
}
