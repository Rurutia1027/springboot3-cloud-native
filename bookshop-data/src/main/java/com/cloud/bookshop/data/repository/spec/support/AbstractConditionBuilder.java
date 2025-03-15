package com.cloud.bookshop.data.repository.spec.support;

import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;

import java.util.Collection;
import java.util.Date;

@Data
public abstract class AbstractConditionBuilder<T> {
    protected void addInConditionToColumn(QueryWrapper<T> queryWrapper, String column, Object values) {
        if (needAddCondition(values)) {
            Path<?> fieldPath = getPath(queryWrapper.getRoot(), column);
            if (values.getClass().isArray()) {
                queryWrapper.addPredicate(fieldPath.in((Object[]) values));
            } else if (values instanceof Collection) {
                queryWrapper.addPredicate(fieldPath.in((Collection<?>) values));
            }
        }
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    protected void addBetweenConditionToColumn(QueryWrapper<T> queryWrapper, String column, Comparable minValue,
                                               Comparable maxValue) {
        if (minValue != null || maxValue != null) {
            Path<? extends Comparable> fieldPath = getPath(queryWrapper.getRoot(), column);
            if (minValue != null && maxValue != null) {
                queryWrapper.addPredicate(queryWrapper.getCb().between(fieldPath, minValue, processMaxValueOnDate(maxValue)));
            } else if (minValue != null) {
                queryWrapper.addPredicate(queryWrapper.getCb().greaterThanOrEqualTo(fieldPath, minValue));
            } else if (maxValue != null) {
                queryWrapper.addPredicate(queryWrapper.getCb().lessThanOrEqualTo(fieldPath, processMaxValueOnDate(maxValue)));
            }
        }
    }


    @SuppressWarnings("rawtypes")
    private Comparable processMaxValueOnDate(Comparable maxValue) {
        if (maxValue instanceof Date) {
            maxValue = new DateTime(maxValue).withTimeAtStartOfDay().plusDays(1).plusSeconds(-1).toDate();
        }
        return maxValue;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected void addGreaterThanConditionToColumn(QueryWrapper<T> queryWrapper, String column, Comparable minValue) {
        if (minValue != null) {
            Path<? extends Comparable> fieldPath = getPath(queryWrapper.getRoot(), column);
            queryWrapper.addPredicate(queryWrapper.getCb().greaterThan(fieldPath, minValue));
        }
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected void addGreaterThanOrEqualConditionToColumn(QueryWrapper<T> queryWrapper, String column,
                                                          Comparable minValue) {
        if (minValue != null) {
            Path<? extends Comparable> fieldPath = getPath(queryWrapper.getRoot(), column);
            queryWrapper.addPredicate(queryWrapper.getCb().greaterThanOrEqualTo(fieldPath, minValue));
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected void addLessThanConditionToColumn(QueryWrapper<T> queryWrapper, String column, Comparable maxValue) {
        if (maxValue != null) {
            Path<? extends Comparable> fieldPath = getPath(queryWrapper.getRoot(), column);
            queryWrapper.addPredicate(queryWrapper.getCb().lessThan(fieldPath, processMaxValueOnDate(maxValue)));
        }
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    protected void addLessThanOrEqualConditionToColumn(QueryWrapper<T> queryWrapper, String column, Comparable maxValue) {
        if (maxValue != null) {
            Path<? extends Comparable> fieldPath = getPath(queryWrapper.getRoot(), column);
            queryWrapper.addPredicate(queryWrapper.getCb().lessThanOrEqualTo(fieldPath, processMaxValueOnDate(maxValue)));
        }
    }

    protected void addLikeConditionToColumn(QueryWrapper<T> queryWrapper, String column, String value) {
        if (StringUtils.isNotBlank(value)) {
            queryWrapper.addPredicate(createLikeCondition(queryWrapper, column, value));
        }
    }

    @SuppressWarnings("unchecked")
    protected Predicate createLikeCondition(QueryWrapper<T> queryWrapper, String column, String value) {
        Path<String> fieldPath = getPath(queryWrapper.getRoot(), column);
        Predicate condition = queryWrapper.getCb().like(fieldPath, "%" + value + "%");
        return condition;
    }

    @SuppressWarnings("unchecked")
    protected void addStartsWidthConditionToColumn(QueryWrapper<T> queryWrapper, String column, String value) {
        if (StringUtils.isNotBlank(value)) {
            Path<String> fieldPath = getPath(queryWrapper.getRoot(), column);
            queryWrapper.addPredicate(queryWrapper.getCb().like(fieldPath, value + "%"));
        }
    }

    protected void addEqualsConditionToColumn(QueryWrapper<T> queryWrapper, String column, Object value) {
        if (needAddCondition(value)) {
            Path<?> fieldPath = getPath(queryWrapper.getRoot(), column);
            queryWrapper.addPredicate(queryWrapper.getCb().equal(fieldPath, value));
        }
    }

    protected void addNotEqualsConditionToColumn(QueryWrapper<T> queryWrapper, String column, Object value) {
        if (needAddCondition(value)) {
            Path<?> fieldPath = getPath(queryWrapper.getRoot(), column);
            queryWrapper.addPredicate(queryWrapper.getCb().notEqual(fieldPath, value));
        }
    }

    @SuppressWarnings("rawtypes")
    protected Path getPath(Root root, String property) {
        String[] names = StringUtils.split(property, ".");
        Path path = root.get(names[0]);
        for (int i = 1; i < names.length; i++) {
            path = path.get(names[i]);
        }
        return path;
    }

    @SuppressWarnings("rawtypes")
    protected boolean needAddCondition(Object value) {
        boolean addCondition = false;
        if (value != null) {
            if (value instanceof String) {
                if (StringUtils.isNotBlank(value.toString())) {
                    addCondition = true;
                }
            } else if (value.getClass().isArray()) {
                if (ArrayUtils.isNotEmpty((Object[]) value)) {
                    addCondition = true;
                }
            } else if (value instanceof Collection) {
                if (CollectionUtils.isNotEmpty((Collection) value)) {
                    addCondition = true;
                }
            } else {
                addCondition = true;
            }
        }
        return addCondition;
    }

}