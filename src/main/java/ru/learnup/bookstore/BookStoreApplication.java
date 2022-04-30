package ru.learnup.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.learnup.bookstore.controlers.rest.UserController;
import ru.learnup.bookstore.dao.entity.Product;
import ru.learnup.bookstore.dao.repository.RolesRepository;
import ru.learnup.bookstore.dao.service.*;
import ru.learnup.bookstore.dao.user.Role;
import ru.learnup.bookstore.dao.user.User;

import java.util.List;
import java.util.Set;


@SpringBootApplication
public class BookStoreApplication extends SpringBootServletInitializer {

    public static final Logger log = LoggerFactory.getLogger(BookStoreApplication.class);

    public static void main(String[] args) throws Exception {

        ConfigurableApplicationContext context = SpringApplication.run(BookStoreApplication.class, args);

        BookService bookService = context.getBean(BookService.class);

        UserService userService = context.getBean(UserService.class);

        RolesRepository rolesRepository = context.getBean(RolesRepository.class);

        ProductService productService = context.getBean(ProductService.class);

        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);

//        bookService.createCatalog();
//
//        log.info("Product 1: {}", productService.findProductById(5L));
//
//        log.info("books: {}", bookService.getBooks());
//
//        String findAuthor = "Лев Толстой";
//        log.info("Все книги автора {}: {}", findAuthor, bookService.findByAuthor(findAuthor));
//
//        Product product = productService.findProductById(1L).orElseThrow(Exception::new);
//        product.setText("Text " + product.getVersion());
//        log.info("Текущее состояние продукта: {}", product.toString());
//        productService.updateProduct(product);
//        Product product1 = productService.findProductById(1L).orElseThrow(Exception::new);
//        product1.setText("Text " + product1.getVersion());
//        log.info("Cостояние продукта после апдэйта: {}", product1.toString());

        User user = new User();
        user.setUserName("admin");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setRoles(Set.of(new Role("ROLE_ADMIN")));
        userService.addUser(user);

    }
}
