package com.cloud.bookshop.repository;

import com.cloud.bookshop.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
