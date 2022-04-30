package ru.learnup.bookstore.dao.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.learnup.bookstore.BookStoreApplication;
import ru.learnup.bookstore.dao.repository.RolesRepository;
import ru.learnup.bookstore.dao.service.UserService;
import ru.learnup.bookstore.dao.user.Role;
import ru.learnup.bookstore.dao.user.User;
import ru.learnup.bookstore.view.RoleView;
import ru.learnup.bookstore.view.UserView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserViewMapper {

    public static final Logger log = LoggerFactory.getLogger(BookStoreApplication.class);

    private final UserService userService;

    private final RolesRepository rolesRepository;

    private final PasswordEncoder passwordEncoder;

    public UserViewMapper(UserService userService, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public UserView mapUserToView(User user) {
        UserView view = new UserView();
        view.setLogin(user.getUsername());
        view.setPassword(user.getPassword());
        view.setRoles(
                user.getRoles()
                        .stream()
                        .map(Role::getRole)
                        .map(RoleView::new)
                        .collect(Collectors.toSet())
        );
        return view;
    }

    public User mapUserFromView(UserView view) {
        User user = new User();
        user.setUserName(view.getLogin());
        user.setPassword(passwordEncoder.encode(view.getPassword()));

        Set<Role> viewRoles = view.getRoles()
                .stream()
                .map(RoleView::getRole)
                .map(Role::new)
                .collect(Collectors.toSet());

        List<Role> allRoles = rolesRepository.findAll();
        Set<Role> finalRoles = new HashSet<>();
        if (allRoles != null) {
            for (Role r : viewRoles) {
                if (!allRoles.contains(r)) {
                    Role role = new Role();
                    role.setRole(r.getRole());
                    finalRoles.add(role);
                }
                else {
                    finalRoles.add(r);
                }
            }
        } else {
            finalRoles = viewRoles;
        }
        user.setRoles(finalRoles);

//        user.setRoles(viewRoles);
//        viewRoles.forEach(user::addRole);
        return user;
    }
}
