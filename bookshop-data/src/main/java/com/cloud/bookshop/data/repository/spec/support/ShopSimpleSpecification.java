package com.cloud.bookshop.data.repository.spec.support;

import com.google.common.collect.Lists;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;

public abstract class ShopSimpleSpecification<T, C>
        extends AbstractEventConditionBuilder<T, C>
        implements Specification<T> {
    public ShopSimpleSpecification(C condition) {
        super(condition);
    }

    // construct query condition
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        if (Long.class != query.getResultType()) {
            addFetch(root);
        }

        List<Predicate> predicates = Lists.newArrayList();
        QueryWrapper<T> queryWrapper = new QueryWrapper<T>(root, query, cb, predicates);
        Predicate permissionCondition = getPermissionConditions(queryWrapper);
        if (Objects.nonNull(permissionCondition)) {
            queryWrapper.addPredicate(permissionCondition);
        }
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    protected Predicate getPermissionConditions(QueryWrapper<T> queryWrapper) {
        return null;
    }

    protected void addFetch(Root<T> root) {
    }

    protected abstract void addCondition(QueryWrapper<T> queryWrapper);
}
