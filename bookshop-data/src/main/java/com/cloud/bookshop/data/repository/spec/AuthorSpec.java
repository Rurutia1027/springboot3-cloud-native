package com.cloud.bookshop.data.repository.spec;

import com.cloud.bookshop.data.domain.Author;
import com.cloud.bookshop.data.repository.spec.support.QueryWrapper;
import com.cloud.bookshop.data.repository.spec.support.ShopSimpleSpecification;
import com.cloud.bookshop.dubbo.dto.AuthorCondition;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang.StringUtils;


public class AuthorSpec extends ShopSimpleSpecification<Author, AuthorCondition> {
    public AuthorSpec(AuthorCondition condition) {
        super(condition);
    }

    @Override
    protected void addCondition(QueryWrapper<Author> queryWrapper) {
        if (StringUtils.isNotBlank(getCondition().getName())) {
            Predicate nameLike = createLikeCondition(queryWrapper, "name",
                    getCondition().getName());
            Predicate emailLike = createLikeCondition(queryWrapper, "email",
                    getCondition().getName());

            addBetweenCondition(queryWrapper, "age");
            addEqualsCondition(queryWrapper, "gender");
            addEqualsConditionToColumn(queryWrapper, "enable", true);
        }
    }

    @Override
    protected void addFetch(Root<Author> root) {
        super.addFetch(root);
    }
}