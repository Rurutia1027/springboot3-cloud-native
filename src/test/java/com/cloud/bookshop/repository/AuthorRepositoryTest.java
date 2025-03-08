package com.cloud.bookshop.repository;

import com.cloud.bookshop.BaseTest;
import com.cloud.bookshop.domain.Author;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class AuthorRepositoryTest extends BaseTest {

    @Autowired
    private AuthorRepository authorRepository;


    @Test
    public void testHibernateEmailValidator() {
        Author a = new Author();
        String aName = UUID.randomUUID().toString();
        a.setName(aName);
        String email = "@#$%^&*(";
        a.setEmail(email);

        Assertions.assertThrowsExactly(ConstraintViolationException.class,
                () -> authorRepository.saveAndFlush(a));
    }
}
