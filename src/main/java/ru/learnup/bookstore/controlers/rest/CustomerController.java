package ru.learnup.bookstore.controlers.rest;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.learnup.bookstore.dao.entity.Customer;
import ru.learnup.bookstore.dao.entity.User;
import ru.learnup.bookstore.dao.mapper.CustomerViewMapper;
import ru.learnup.bookstore.dao.service.CustomerService;
import ru.learnup.bookstore.dao.service.UserService;
import ru.learnup.bookstore.view.CustomerView;
import ru.learnup.bookstore.view.UserCartView;
import ru.learnup.bookstore.view.UserView;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final UserService userService;
    private final CustomerService customerService;
    private final CustomerViewMapper mapper;

    public CustomerController(UserService userService, CustomerService customerService, CustomerViewMapper mapper) {
        this.userService = userService;
        this.customerService = customerService;
        this.mapper = mapper;
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping
    public List<CustomerView> getCustomers() {
        List<User> users = userService.findAll();
        List<CustomerView> customers = new ArrayList<>();
        for (User user : users) {
            Customer customer = user.getCustomer();
            CustomerView view = mapper.mapCustomerToView(customer);
            customers.add(view);
        }
        return customers;
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{login}")
    public CustomerView updateCustomer(@PathVariable("login") String login,
                               @RequestBody CustomerView body) {
        if (body.getUser().getLogin() == null) {
            throw new EntityNotFoundException("Try to found null entity");
        }
        if (!Objects.equals(login, body.getUser().getLogin())) {
            throw new RuntimeException("Entity has bad login");
        }
        User user = userService.findUserByLogin(login);
        if (user == null) {
            throw new EntityNotFoundException("There are no authors with this login ");
        }
        Customer customer = user.getCustomer();
        if (!customer.getName().equals(body.getName())) {
            customer.setName(body.getName());
        }
        if (!customer.getSurname().equals(body.getSurname())) {
            customer.setSurname(body.getSurname());
        }
        customerService.addCustomer(customer);
        return mapper.mapCustomerToView(customer);
    }

}
