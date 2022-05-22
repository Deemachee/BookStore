package ru.learnup.bookstore.dao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.learnup.bookstore.BookStoreApplication;
import ru.learnup.bookstore.dao.repository.RolesRepository;
import ru.learnup.bookstore.dao.repository.UserRepository;
import ru.learnup.bookstore.dao.entity.Role;
import ru.learnup.bookstore.dao.entity.User;

import javax.persistence.EntityExistsException;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    public static final Logger log = LoggerFactory.getLogger(BookStoreApplication.class);

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            RolesRepository rolesRepository, PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Transactional
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void create(User user) {

        User exist = userRepository.findByUserName(user.getUsername());
        if (exist != null) {
            throw new EntityExistsException("user with login "
                    + user.getUsername() + " already exist");
        }
        String password = passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        Set<String> roles = user.getRoles().stream()
                .map(Role::getRole)
                .collect(Collectors.toSet());

        Set<Role> existRoles = rolesRepository.findByRoleIn(roles);

        user.setRoles(existRoles);
        existRoles.forEach(role -> role.setUsers(Set.of(user)));
        userRepository.save(user);
    }


    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public void addRole(Role role) {
        List<Role> allRoles = rolesRepository.findAll();
        boolean exist = false;
        for (Role r : allRoles) {
            if (r.getRole().equals(role.getRole())) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            rolesRepository.save(role);
            log.info("Role <<{}>> was added to database", role.getRole());
        }
    }

    @Transactional
    public Boolean deleteUser(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    @Transactional
    @Lock(value = LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    public User update(User user) {
        try {
            return userRepository.save(user);
        } catch (OptimisticLockException e) {
            log.warn("Optimistic lock exception for update user {}", user.getId());
            throw e;
        }
    }

//    PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
//
//        UserService userService = context.getBean(UserService.class);
//
//        BookService bookService = context.getBean(BookService.class);
//
//        bookService.createCatalog();
//
//        User user = new User();
//        user.setUserName("admin");
//        user.setPassword(passwordEncoder.encode("admin"));
//        user.setRoles(Set.of(new Role("ROLE_ADMIN")));
//        Customer customer = new Customer();
//        customer.setName("Василий");
//        customer.setSurname("Пупкин");
//        user.setCustomer(customer);
//        userService.addUser(user);
}
