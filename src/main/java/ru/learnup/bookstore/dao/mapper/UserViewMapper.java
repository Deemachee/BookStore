package ru.learnup.bookstore.dao.mapper;

import org.springframework.stereotype.Component;
import ru.learnup.bookstore.dao.entity.Role;
import ru.learnup.bookstore.dao.entity.User;
import ru.learnup.bookstore.view.CustomerUserView;
import ru.learnup.bookstore.view.RoleView;
import ru.learnup.bookstore.view.UserView;
import java.util.stream.Collectors;

@Component
public class UserViewMapper {

    public UserView mapUserToView(User user) {
        UserView view = new UserView();
        view.setLogin(user.getUsername());
        view.setPassword(user.getPassword());
        view.setCustomer(new CustomerUserView(user.getCustomer().getName(), user.getCustomer().getSurname()));
        view.setRoles(
                user.getRoles()
                        .stream()
                        .map(Role::getRole)
                        .map(RoleView::new)
                        .collect(Collectors.toSet())
        );
        return view;
    }
}
