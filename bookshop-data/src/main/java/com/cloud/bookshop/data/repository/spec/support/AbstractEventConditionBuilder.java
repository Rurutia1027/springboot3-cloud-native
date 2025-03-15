package com.cloud.bookshop.data.repository.spec.support;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;

@Data
@NoArgsConstructor
public abstract class AbstractEventConditionBuilder<T, C> extends AbstractConditionBuilder<T> {

    private C condition;

    public AbstractEventConditionBuilder(C condition) {
        this.condition = condition;
    }

    protected void addLikeCondition(QueryWrapper<T> queryWrapper, String field) {
        addLikeCondition(queryWrapper, field, field);
    }

    protected void addLikeCondition(QueryWrapper<T> queryWrapper, String field, String column) {
        addLikeConditionToColumn(queryWrapper, column, (String) getValue(getCondition(), field));
    }


    protected void addStartsWidthCondition(QueryWrapper<T> queryWrapper, String field) {
        addStartsWidthCondition(queryWrapper, field, field);
    }


    protected void addStartsWidthCondition(QueryWrapper<T> queryWrapper, String field, String column) {
        addStartsWidthConditionToColumn(queryWrapper, column, (String) getValue(getCondition(), field));
    }

    protected void addEqualsCondition(QueryWrapper<T> queryWrapper, String field) {
        addEqualsCondition(queryWrapper, field, field);
    }

    protected void addEqualsCondition(QueryWrapper<T> queryWrapper, String field, String column) {
        addEqualsConditionToColumn(queryWrapper, column, getValue(getCondition(), field));
    }

    protected void addNotEqualsCondition(QueryWrapper<T> queryWrapper, String field) {
        addNotEqualsCondition(queryWrapper, field, field);
    }


    protected void addNotEqualsCondition(QueryWrapper<T> queryWrapper, String field, String column) {
        addNotEqualsConditionToColumn(queryWrapper, column, getValue(getCondition(), field));
    }

    protected void addInCondition(QueryWrapper<T> queryWrapper, String field) {
        addInCondition(queryWrapper, field, field);
    }

    protected void addInCondition(QueryWrapper<T> queryWrapper, String field, String column) {
        addInConditionToColumn(queryWrapper, column, getValue(getCondition(), field));
    }

    protected void addBetweenCondition(QueryWrapper<T> queryWrapper, String field) {
        addBetweenCondition(queryWrapper, field, field + "To", field);
    }


    @SuppressWarnings("rawtypes")
    protected void addBetweenCondition(QueryWrapper<T> queryWrapper, String startField, String endField, String column) {
        addBetweenConditionToColumn(queryWrapper, column, (Comparable) getValue(getCondition(), startField), (Comparable) getValue(getCondition(), endField));
    }


    protected void addGreaterThanCondition(QueryWrapper<T> queryWrapper, String field) {
        addGreaterThanCondition(queryWrapper, field, field);
    }


    @SuppressWarnings("rawtypes")
    protected void addGreaterThanCondition(QueryWrapper<T> queryWrapper, String field, String column) {
        addGreaterThanConditionToColumn(queryWrapper, column, (Comparable) getValue(getCondition(), field));
    }

    protected void addGreaterThanOrEqualCondition(QueryWrapper<T> queryWrapper, String field) {
        addGreaterThanOrEqualCondition(queryWrapper, field, field);
    }

    @SuppressWarnings("rawtypes")
    protected void addGreaterThanOrEqualCondition(QueryWrapper<T> queryWrapper, String field, String column) {
        addGreaterThanOrEqualConditionToColumn(queryWrapper, column, (Comparable) getValue(getCondition(), field));
    }

    protected void addLessThanCondition(QueryWrapper<T> queryWrapper, String field) {
        addLessThanCondition(queryWrapper, field, field);
    }

    @SuppressWarnings("rawtypes")
    protected void addLessThanCondition(QueryWrapper<T> queryWrapper, String field, String column) {
        addLessThanConditionToColumn(queryWrapper, column, (Comparable) getValue(getCondition(), field));
    }

    protected void addLessThanOrEqualCondition(QueryWrapper<T> queryWrapper, String field) {
        addLessThanOrEqualCondition(queryWrapper, field, field);
    }

    @SuppressWarnings("rawtypes")
    protected void addLessThanOrEqualCondition(QueryWrapper<T> queryWrapper, String field, String column) {
        addLessThanOrEqualConditionToColumn(queryWrapper, column, (Comparable) getValue(getCondition(), field));
    }

    private Object getValue(C condition, String field) {
        try {
            return PropertyUtils.getProperty(condition, field);
        } catch (IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


}