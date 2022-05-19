package ru.learnup.bookstore.dao.mapper;

import org.springframework.stereotype.Component;
import ru.learnup.bookstore.dao.entity.Customer;
import ru.learnup.bookstore.view.CustomerView;
import ru.learnup.bookstore.view.UserCartView;


@Component
public class CustomerViewMapper {

    public CustomerView mapCustomerToView(Customer customer) {
        CustomerView view = new CustomerView();
        view.setName(customer.getName());
        view.setSurname(customer.getSurname());
        view.setUser(new UserCartView(customer.getUser().getUsername()));
        return view;
    }
}
