package ru.learnup.bookstore.controlers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.learnup.bookstore.dao.repository.UserRolesRepository;
import ru.learnup.bookstore.dao.service.UserService;
import ru.learnup.bookstore.dao.user.User;
import ru.learnup.bookstore.dao.user.UserRole;
import ru.learnup.bookstore.view.RoleView;
import ru.learnup.bookstore.view.UserView;

import javax.management.relation.Role;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRolesRepository userRolesRepository;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, UserRolesRepository userRolesRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRolesRepository = userRolesRepository;
    }

    @PostMapping
    public boolean createUser(@RequestBody UserView userView) {
        User entity = new User();
        entity.setUserName(userView.getLogin());
        String password = passwordEncoder.encode(userView.getPassword());
        entity.setPassword(userView.getPassword());
        Set<UserRole> roles = userView.getRoles()
                .stream()
                .map(RoleView::getRole)
                .map(UserRole::new)
                .collect(Collectors.toSet());
        entity.setRoles(roles);
        roles.forEach(role -> role.setUsers(Set.of(entity)));
        userRolesRepository.saveAll(roles);
        userService.create(entity);
        return true;
    }
}
