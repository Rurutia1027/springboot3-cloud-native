package com.cloud.bookshop.data.repository.spec.support;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QueryWrapper<T> {
    /**
     * JPA Root
     */
    private Root<T> root;
    /**
     * JPA CriteriaBuilder
     */
    private CriteriaBuilder cb;
    /**
     * JPA Predicate Collection
     */
    private List<Predicate> predicates;
    /**
     * JPA Query Object
     */
    private CriteriaQuery<?> query;

    public QueryWrapper(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb, List<Predicate> predicates) {
        this.root = root;
        this.query = query;
        this.cb = cb;
        this.predicates = predicates;
    }

    public void addPredicate(Predicate predicate) {
        this.predicates.add(predicate);
    }
}