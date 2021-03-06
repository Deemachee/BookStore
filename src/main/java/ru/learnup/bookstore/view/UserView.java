package ru.learnup.bookstore.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.learnup.bookstore.dao.entity.Customer;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserView {

    private String login;

    private String password;

    private CustomerUserView customer;

    private Set<RoleView> roles;
}
