package ru.learnup.bookstore.dao.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import ru.learnup.bookstore.dao.entity.Customer;
import ru.learnup.bookstore.dao.repository.CustomerRepository;

import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class CustomerService {

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private final CustomerRepository customerRepository;

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    @Lock(value = LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    public Customer addCustomer(Customer customer) {
        try {
            return customerRepository.save(customer);
        } catch (
                OptimisticLockException e) {
            log.warn("Optimistic lock exception for update customer {}", customer.getId());
            throw e;
        }
    }

    public Customer getCustomerById(long id) {
        return customerRepository.getById(id);
    }


}
