package ru.learnup.bookstore.specifications;

import org.springframework.data.jpa.domain.Specification;
import ru.learnup.bookstore.dao.entity.Book;
import ru.learnup.bookstore.dao.service.BookFilter;


import javax.persistence.criteria.Predicate;


public class BookSpecification  {

    public static Specification<Book> byFilter(BookFilter filter) {
        return (root, q, cb) -> {
            Predicate predicate = cb.isNotNull(root.get("id"));
            if (filter.getTitle() != null) {
               predicate = cb.and(predicate, cb.like(root.get("title"), "%" + filter.getTitle() + "%"));
            }
            return predicate;
        };
    }


}
