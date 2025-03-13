package com.cloud.bookshop.data.repository;

import com.cloud.bookshop.data.BaseTest;
import com.cloud.bookshop.data.domain.Book;
import com.cloud.bookshop.data.domain.Category;
import com.cloud.bookshop.data.domain.EBook;
import com.cloud.bookshop.data.domain.PrintBook;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class BookRepositoryTest extends BaseTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PrintBookRepository printBookRepository;

    @Test
    public void testOptimisticLockingViaVersionAnnotationSolution() {
        Book book = new Book();
        String bName = UUID.randomUUID().toString();
        Book bookRet = bookRepository.saveAndFlush(book);
        Assertions.assertNotNull(bookRet);
        Assertions.assertTrue(bookRet.getId() > 0);

        Book bookInTransaction1 = bookRepository.findById(book.getId()).get();
        bookInTransaction1.setName("UpdatedT1");
        bookRepository.save(bookInTransaction1);

        Book bookInTransaction2 = bookRepository.findById(book.getId()).get();
        bookInTransaction2.setName("UpdatedT2");

        try {
            bookRepository.save(bookInTransaction2);
        } catch (OptimisticLockException e) {
            Assertions.assertTrue(e.getMessage().contains("Object of class"));
        }

    }

    @Test
    public void testPrintBookRepository() {
        PrintBook printBook = new PrintBook();
        PrintBook ret = printBookRepository.saveAndFlush(printBook);
        Assertions.assertTrue(ret.getId() > 0);
        Book ret2 = bookRepository.findById(ret.getId()).get();
        Assertions.assertNotNull(ret2);
        List<PrintBook> printBooks = printBookRepository.findAll();
        Assertions.assertTrue(printBooks.size() > 0);
    }

    @Test
    public void testJPAFind() {
        Book book = new Book();
        String bookName = UUID.randomUUID().toString();
        book.setName(bookName);
        bookRepository.save(book);

        Book bookRet = bookRepository.findByName(bookName);
        Assertions.assertNotNull(bookRet);
        Assertions.assertEquals(bookRet.getName(), bookName);
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
        Assertions.assertTrue(bookPage.getTotalElements() >= 0);
        Assertions.assertTrue(bookPage.getTotalPages() >= 0);
        Assertions.assertTrue(bookPage.getSize() >= 0);
        Assertions.assertEquals(bookPage.getSort(), Sort.by(Sort.Order.desc("name")));
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

    @Test
    public void testExampleMatcherWithString() {
        String bookName = UUID.randomUUID().toString();
        Book book = new Book();
        book.setName(bookName);

        // save book to db
        Book bookRet = bookRepository.save(book);
        Assertions.assertTrue(bookRet.getId() > 0);

        // create example matcher and configure it's matching strategy
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        // extract book name fraction here to verify the string matcher's CONTAINING options works as expected
        String bookNameFraction = bookName.substring(0, bookName.length() - 4);
        Example<Book> expBook = Example.of(book, matcher);

        // adopt example matcher to book repository via example matcher and pageable configured query condition
        Pageable pageable = PageRequest.of(0, 3, Sort.by(new Sort.Order(Sort.Direction.DESC, "name")));
        Page<Book> bookPage = bookRepository.findAll(expBook, pageable);
        Assertions.assertTrue(bookPage.getTotalPages() > 0);
        Assertions.assertTrue(bookPage.hasContent());
        Assertions.assertTrue(bookPage.getContent().size() > 0);
        Book queryBookItem = bookPage.getContent().get(0);
        Assertions.assertEquals(queryBookItem.getName(), bookName);
    }

    // test case to show how to use JpaSpecificationExecutor to execute dynamic query
    @Test
    public void testJpaSpecificationWithSingleCondition() {
        // first, create mock book instance and save to db
        String bookName = UUID.randomUUID().toString();
        Book book = new Book();
        book.setName(bookName);

        Book bookRet = bookRepository.save(book);
        Assertions.assertEquals(book.getName(), bookRet.getName());


        // then, we need to define a Specification for Book and declare its query conditions -- in this case, we query via book name
        Specification<Book> spec = new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // we wanna query based on book name
                // here the root is an encapsulation of Book
                return criteriaBuilder.equal(root.get("name"), bookName);
            }
        };


        // then we pass the spec to the bookRepository
        Book bookRet2 = bookRepository.findOne(spec).get();
        Assertions.assertNotEquals(null, bookRet2);
        Assertions.assertEquals(bookName, bookRet2.getName());

    }

    @Test
    public void testJpaSpecificationWithMultipleQueryCondition() {
        // first create two book item
        String bookName = UUID.randomUUID().toString();
        String categoryName = UUID.randomUUID().toString();

        Book b1 = new Book();
        b1.setName(bookName);

        // Category - Book = 1 : N
        Category category = new Category();
        category.setName(categoryName);
        category.setBookList(List.of(b1));
        // save category first
        Category categoryRet = categoryRepository.save(category);
        Assertions.assertTrue(categoryRet.getId() > 0 && category.getBookList().size() > 0);

        // then save book
        b1.setCategory(category);
        Book bookRet = bookRepository.save(b1);
        Assertions.assertTrue(Objects.nonNull(bookRet));
        Assertions.assertTrue(bookRet.getCategory().getName().equals(categoryName));
        // then we define the Specification for dynamic query
        Specification<Book> spec = new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                // condition 1
                Predicate p1 = criteriaBuilder.equal(root.get("name"), bookName);
                // condition 2
                Predicate p2 = criteriaBuilder.equal(root.get("category"), category);

                // combine the two conditions together via AND
                Predicate p3 = criteriaBuilder.and(p1, p2);

                return p3;
            }
        };

        // then query via the spec we combine the query conditions with 'AND' relationship
        List<Book> queryBooks = bookRepository.findAll(spec);
        Assertions.assertTrue(queryBooks.size() > 0);
        Assertions.assertEquals(queryBooks.get(0).getName(), bookName);
        Assertions.assertEquals(queryBooks.get(0).getCategory().getName(), categoryName);
    }


    // we use transaction commit to commit update to dataabse
    @Test
    public void testPersistenceContext() {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        Book book = new Book();
        String bookName = UUID.randomUUID().toString();
        book.setName(bookName);
        Book bookRet = bookRepository.save(book);

        Book bookQuery = bookRepository.findByName(bookName);
        Assertions.assertEquals(bookQuery.getName(), bookName);


        // update queried book's fields -- this will result in JPA's persistence context cached value diff from database table's record
        // when transaction executes commit, we will see a update SQL command in log info -- this is called dirty check
        bookQuery.setCreateTime(new Date());
        // update the create-time will be executed by the transaction commit
        // bookRepository.save(bookQuery);

        // here in the process of commit, JPA will check whether the persistence context cached entities status
        // are synchronized with the entities that stored in the database , if there are some distinctions
        // commit will update the persistence context cached latest entities value to database
        transactionManager.commit(status);
        Assertions.assertEquals(bookRet.getName(), bookName);
    }

    // test case show the difference between save & saveAndFlush
    // take JPA's persistence context into consideration.
    // we use @Transactional instead of executing TransactionManager#commit explicitly

    // @Test
    @Transactional
    public void testSaveVsSaveAndFlush() {
        Book book = new Book();
        String bookName = UUID.randomUUID().toString();
        book.setName(bookName);

        Book bookRet = bookRepository.save(book);
        Assertions.assertNotNull(bookRet);
        Assertions.assertEquals(bookRet.getName(), bookName);
        Assertions.assertNotNull(bookRet.getId());

        Book bookSaveWithoutFlush = new Book();
        String bookNameWithoutFlush = UUID.randomUUID().toString();
        bookSaveWithoutFlush.setId(bookRet.getId());
        bookSaveWithoutFlush.setName(bookNameWithoutFlush);

        Book bookSaveWithFlush = new Book();
        String bookNameWithFlush = UUID.randomUUID().toString();
        bookSaveWithFlush.setId(bookRet.getId());
        bookSaveWithFlush.setName(bookNameWithFlush);

        // this operation will cause Optimistic Locking Exception throwing
        try {
            // entity -> persistence context cache -> db disk
            bookRepository.saveAndFlush(bookSaveWithFlush);

            // entity -> persistence context cache
            bookRepository.save(bookSaveWithoutFlush);

            // clear JPA persistence context
            entityManager.clear();
        } catch (OptimisticLockException e) {

        }

        // exeucte query by Id here
        Book finalRet = bookRepository.findById(bookRet.getId()).get();
        Assertions.assertNotNull(finalRet);
        Assertions.assertNotNull(finalRet.getId());
        Assertions.assertEquals(bookNameWithFlush, finalRet.getName());
    }

    @Test
    public void testQuerySameEntityTwiceOnlyInvokeOneSQLQuery() {
        Book b = new Book();
        String bn = UUID.randomUUID().toString();
        b.setName(bn);

        Book bRet = bookRepository.saveAndFlush(b);
        Assertions.assertTrue(Objects.nonNull(bRet) && bRet.getId() > 0);

        // query twice only show query DB via SQL command once
        bookRepository.findById(bRet.getId());
        bookRepository.findById(bRet.getId());
    }

    @Test
    public void testFetchStrategy() {
        Category category = new Category();
        String cn = UUID.randomUUID().toString();
        category.setName(cn);

        Book book = new Book();
        String bn = UUID.randomUUID().toString();

        book.setName(bn);
        book.setCategory(category);
        category.setBookList(List.of(book));

        Category categoryRet = categoryRepository.saveAndFlush(category);
        Assertions.assertNotNull(categoryRet);
        Assertions.assertNotNull(categoryRet.getId());

        Book bookRet = bookRepository.saveAndFlush(book);
        Assertions.assertNotNull(bookRet);
        Assertions.assertNotNull(bookRet.getId());

        // we already know that Category : Book = 1 : N
        // and to avoid creation join table, we let Book side to maintain the relationship
        // all 1:N mapping relationships are stored to Book#bs_category_bs_id field as foreign key
        Book bookQueryRet1 = bookRepository.findByName(bookRet.getName());
        // on the Book side, when declaring the @ManyToOne() we also refer the fetch strategy as 'EAGER'
        // which means every time we query Book(s) it's associated Category entity(all fields)
        // will be fetched and loaded from database to memory

        Assertions.assertNotNull(bookQueryRet1.getCategory());
        Assertions.assertNotNull(bookQueryRet1.getCategory().getName());
    }


    @Test
    public void testInheritanceEntityRelationship() {
        EBook b1 = new EBook();
        String bName1 = UUID.randomUUID().toString();

        PrintBook b2 = new PrintBook();
        String bName2 = UUID.randomUUID().toString();

        // After we create Book's two sub-classes and JPA will not create extra tables in db
        // instead it will add sub-classes' new fields to the book table
        // but there is an extra field that with the name of dtype
        // then if we insert EBook dtype = EBook, and PrintBook for dtype = PrintBook
        bookRepository.saveAndFlush(b1);
        bookRepository.saveAndFlush(b2);

        // here we query all books from database and get each dtype and print
        List<Book> books = bookRepository.findAll();
        Assertions.assertTrue(Objects.nonNull(books) && books.size() > 0);
    }
}
