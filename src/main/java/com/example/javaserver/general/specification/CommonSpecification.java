package com.example.javaserver.general.specification;

import com.example.javaserver.general.criteria.SearchCriteria;
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
        String subKey = null;
        if (key.matches("^\\w+Id$")) {
            key = key.substring(0, key.length() - 2);
            subKey = "id";
        }
        Object value = criteria.getValue();

        switch (operation) {
            case "==": return builder.equal(subKey == null ? root.get(key) : root.get(key).get(subKey), value.toString());
            case "!=": return builder.notEqual(subKey == null ? root.get(key) : root.get(key).get(subKey), value.toString());
            case ">": return builder.greaterThan(subKey == null ? root.get(key) : root.get(key).get(subKey), value.toString());
            case "<": return builder.lessThan(subKey == null ? root.get(key) : root.get(key).get(subKey), value.toString());
            case ">=": return builder.greaterThanOrEqualTo(subKey == null ? root.get(key) : root.get(key).get(subKey), value.toString());
            case "<=": return builder.lessThanOrEqualTo(subKey == null ? root.get(key) : root.get(key).get(subKey), value.toString());
            case ":": return builder.like(subKey == null ? root.get(key) : root.get(key).get(subKey), value.toString());
            case "isNull": return builder.isNull(subKey == null ? root.get(key) : root.get(key).get(subKey));
            case "isNotNull": return builder.isNotNull(subKey == null ? root.get(key) : root.get(key).get(subKey));
            case "isTrue": return builder.isTrue(subKey == null ? root.get(key) : root.get(key).get(subKey));
            case "isFalse": return builder.isFalse(subKey == null ? root.get(key) : root.get(key).get(subKey));
            default: throw new IllegalStateException("Некорректный оператор сравнения");
        }
    }
}
