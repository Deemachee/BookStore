package ru.learnup.bookstore.dao.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.learnup.bookstore.dao.repository.UserRepository;
import ru.learnup.bookstore.dao.repository.UserRolesRepository;
import ru.learnup.bookstore.dao.user.User;
import ru.learnup.bookstore.dao.user.UserRole;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final UserRolesRepository userRolesRepository;

//    private final PasswordEncoder passwordEncoder;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public UserService(UserRepository userRepository, UserRolesRepository userRolesRepository) {
        this.userRepository = userRepository;
        this.userRolesRepository = userRolesRepository;
//        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserName(username);
    }

    public void create(User user) {
//        User findUser = userRepository.findByUserName(user.getUsername());
//        if (findUser != null) {
//            throw new EntityExistsException("login " + user.getUsername() + " is already exists");
//        }
//        String password = passwordEncoder.encode(user.getPassword());
//        user.setPassword(password);
//
//        Set<String> roles = user.getRoles()
//                .stream()
//                .map(UserRole::getRole)
//                .collect(Collectors.toSet()
//                );
//        UserRole role = new UserRole();
//        Set<UserRole> existRoles = userRolesRepository.findByRoleIn(roles);
//        user.setRoles(existRoles);
//        existRoles.forEach(userRole -> userRole.setUsers(Set.of(user)));
//        userRepository.save(user);
    }

    public void addRole(User user, UserRole role) {

    }
}
