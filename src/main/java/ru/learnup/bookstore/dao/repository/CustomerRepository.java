package ru.learnup.bookstore.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.learnup.bookstore.dao.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
