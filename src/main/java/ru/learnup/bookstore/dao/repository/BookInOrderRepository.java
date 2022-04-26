package ru.learnup.bookstore.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.learnup.bookstore.dao.entity.BookInOrder;

public interface BookInOrderRepository extends JpaRepository<BookInOrder, Long> {
}
