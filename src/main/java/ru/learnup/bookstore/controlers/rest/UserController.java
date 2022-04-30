package ru.learnup.bookstore.controlers.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.learnup.bookstore.BookStoreApplication;
import ru.learnup.bookstore.dao.mapper.UserViewMapper;
import ru.learnup.bookstore.dao.service.UserService;
import ru.learnup.bookstore.dao.user.Role;
import ru.learnup.bookstore.dao.user.User;
import ru.learnup.bookstore.view.RoleView;
import ru.learnup.bookstore.view.UserView;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    public static final Logger log = LoggerFactory.getLogger(BookStoreApplication.class);

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserViewMapper userViewMapper;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, UserViewMapper userViewMapper) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userViewMapper = userViewMapper;
    }


//    @Secured("ROLE_ADMIN")
    @PostMapping
    public Boolean createUser(@RequestBody UserView body) {
        User entity = new User();
        entity.setUserName(body.getLogin());
        entity.setPassword(body.getPassword());
        entity.setRoles(
                body.getRoles()
                        .stream()
                        .map(RoleView::getRole)
                        .map(Role::new)
                        .collect(Collectors.toSet())
        );
        userService.create(entity);
        return true;
    }

    @Secured({"ROLE_ADMIN"})
    @GetMapping
    public List<UserView> getUsers() {
        return userService.findAll()
                .stream()
                .map(user -> UserView.builder()
                        .login(user.getUsername())
                        .roles(user.getRoles().stream()
                                .map(RoleView::new)
                                .collect(Collectors.toSet())
                        )
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{login}")
    public Boolean deleteUser(@PathVariable("login") String login) {
        User findUser = userService.findUserByLogin(login);
        return userService.deleteUser(findUser.getId());
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{login}")
    public UserView updateUser(@PathVariable("login") String login,
                               @RequestBody UserView body) {
        if (body.getLogin() == null) {
            throw new EntityNotFoundException("Try to found null entity");
        }
        if (!Objects.equals(login, body.getLogin())) {
            throw new RuntimeException("Entity has bad login");
        }
        User user = userService.findUserByLogin(login);
        if (!user.getPassword().equals(passwordEncoder.encode(body.getPassword()))) {
            user.setPassword(passwordEncoder.encode(body.getPassword()));
        }
        userService.update(user);
        return userViewMapper.mapUserToView(user);
    }
}
