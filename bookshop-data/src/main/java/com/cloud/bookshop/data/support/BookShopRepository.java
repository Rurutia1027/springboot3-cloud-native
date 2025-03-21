package com.cloud.bookshop.data.support;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BookShopRepository<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T>  {
}
