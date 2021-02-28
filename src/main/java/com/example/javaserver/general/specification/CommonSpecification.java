package com.example.javaserver.general.specification;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.Iterator;

public class CommonSpecification<T> implements Specification<T> {
    public static <T> Specification<T> of(Collection<SearchCriteria> criteria) {
        Specification<T> specification = Specification.where(null);
        Iterator<SearchCriteria> it = criteria.iterator();
        if (it.hasNext()) {
            specification = new CommonSpecification<>(it.next());
            while (it.hasNext()) {
                specification = specification.and(new CommonSpecification<>(it.next()));
            }
        }
        return specification;
    }

    private final SearchCriteria criteria;

    public CommonSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(
            Root<T> root,
            CriteriaQuery<?> query,
            CriteriaBuilder builder
    ) {
        String operation = criteria.getOperation();
        String key = criteria.getKey();
        Object value = criteria.getValue();

        Path<String> keyPath = root.get(key);
        String valueString = value.toString();

        switch (operation) {
            case "=": return builder.equal(keyPath, valueString);
            case "!=": return builder.notEqual(keyPath, valueString);
            case ">": return builder.greaterThan(keyPath, valueString);
            case "<": return builder.lessThan(keyPath, valueString);
            case ">=": return builder.greaterThanOrEqualTo(keyPath, valueString);
            case "<=": return builder.lessThanOrEqualTo(keyPath, valueString);
            case ":": return builder.like(keyPath, valueString);
            default: return null;
        }
    }
}
