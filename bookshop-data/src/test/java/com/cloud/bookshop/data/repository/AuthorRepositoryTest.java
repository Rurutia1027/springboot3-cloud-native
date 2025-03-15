package com.cloud.bookshop.data.repository;

import com.cloud.bookshop.data.BaseTest;
import com.cloud.bookshop.data.domain.Author;
import com.cloud.bookshop.data.domain.Gender;
import com.cloud.bookshop.data.repository.spec.AuthorSpec;
import com.cloud.bookshop.dubbo.dto.AuthorCondition;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
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

    @Test
    public void testAuthorSpecificConditionQuery1() {
        String authorName = UUID.randomUUID().toString();
        Author author = new Author();
        author.setName(authorName);

        // first save author to db
        Author ret = authorRepository.save(author);
        Assertions.assertTrue(ret.getId() > 0);

        AuthorCondition condition = new AuthorCondition();
        condition.setName(authorName);
        List<Author> authorList = authorRepository.findAll(new AuthorSpec(condition));
        Assertions.assertTrue(authorList.size() > 0);

    }

    @Test
    public void testAuthorSpecificConditionQuery2() {
        String authorName = UUID.randomUUID().toString();
        Author author = new Author();
        Integer age = 42;
        author.setName(authorName);
        author.setAge(age);


        AuthorCondition condition = new AuthorCondition();
        // append query condition here
        condition.setAge(age);
        condition.setName(authorName);
        // save author to db
        Author authorRet = authorRepository.save(author);
        Assertions.assertTrue(authorRet.getId() > 0);

        // find all based on given condition
        List<Author> authors = authorRepository.findAll(new AuthorSpec(condition));
        Assertions.assertTrue(authors.size() > 0);
    }

    @Test
    public void testAuthorSpecificConditionQuery3() {
        Author author = new Author();
        String authorName = UUID.randomUUID().toString();
        Integer fromAge = 12;
        Integer toAge = 69;
        Gender gender = Gender.FEMALE;

        author.setName(authorName);
        author.setAge(54);
        author.setGender(gender);

        // save author to db
        Author retAuthor = authorRepository.save(author);
        Assertions.assertTrue(retAuthor.getId() > 0);


        // build author query condition
        AuthorCondition condition = new AuthorCondition();
        condition.setName(authorName);
        condition.setAge(fromAge);
        condition.setAgeTo(toAge);
        condition.setGender(com.cloud.bookshop.dubbo.dto.Gender.FEMALE);

        List<Author> authors = authorRepository.findAll(new AuthorSpec(condition));
        Assertions.assertTrue(authors.size() > 0);
    }
}
