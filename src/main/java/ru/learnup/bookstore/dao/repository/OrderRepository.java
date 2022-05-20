package ru.learnup.bookstore.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.learnup.bookstore.dao.entity.Book;
import ru.learnup.bookstore.dao.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
