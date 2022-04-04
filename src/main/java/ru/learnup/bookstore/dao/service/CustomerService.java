package ru.learnup.bookstore.dao.service;

import org.springframework.stereotype.Service;
import ru.learnup.bookstore.dao.entity.Customer;
import ru.learnup.bookstore.dao.repository.CustomerRepository;
import java.util.List;

@Service
public class CustomerService {

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private final CustomerRepository customerRepository;

    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    public Customer addCustomer (Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(long id) {
        return customerRepository.getById(id);
    }

}
