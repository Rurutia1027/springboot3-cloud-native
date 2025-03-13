package com.cloud.bookshop.data.support;

import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

// implement a customized base repository to intercept context info and write context info to log
// declare this as the base repository in Spring Application Entry class: BookstoreBackendApplication
public class BookShopRepositoryImpl<T> extends SimpleJpaRepository<T, Long> {
    public BookShopRepositoryImpl(JpaEntityInformation entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    @Override
    public <S extends T> S save(S entity) {
        // todo: remove system out later after we configure Log4j logger writer to the project
        System.out.println("log to save " + entity.getClass().getSimpleName());
        return super.save(entity);
    }
}
