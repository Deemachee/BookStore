package ru.learnup.bookstore;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import ru.learnup.bookstore.dao.entity.Author;
import ru.learnup.bookstore.dao.entity.Book;
import ru.learnup.bookstore.dao.entity.Product;
import ru.learnup.bookstore.dao.entity.Warehouse;
import ru.learnup.bookstore.dao.repository.UserRolesRepository;
import ru.learnup.bookstore.dao.service.*;
import ru.learnup.bookstore.dao.user.User;
import ru.learnup.bookstore.dao.user.UserRole;

import java.util.List;
import java.util.Set;


@SpringBootApplication
public class BookStoreApplication extends SpringBootServletInitializer {

    public static final Logger log = LoggerFactory.getLogger(BookStoreApplication.class);

    public static void main(String[] args) throws Exception {

        ConfigurableApplicationContext context = SpringApplication.run(BookStoreApplication.class, args);

        BookService bookService = context.getBean(BookService.class);

        UserService userService = context.getBean(UserService.class);

        UserRolesRepository userRolesRepository = context.getBean(UserRolesRepository.class);

        ProductService productService = context.getBean(ProductService.class);

        bookService.createCatalog();

        log.info("Product 1: {}", productService.findProductById(5L));

        log.info("books: {}", bookService.getBooks());

        String findAuthor = "Лев Толстой";
        log.info("Все книги автора {}: {}", findAuthor, bookService.findByAuthor(findAuthor));

        Product product = productService.findProductById(1L).orElseThrow(Exception::new);
        product.setText("Text " + product.getVersion());
        log.info("Текущее состояние продукта: {}", product.toString());
        productService.updateProduct(product);
        Product product1 = productService.findProductById(1L).orElseThrow(Exception::new);
        product1.setText("Text " + product1.getVersion());
        log.info("Cостояние продукта после апдэйта: {}", product1.toString());

        log.info(userService.findAllUsers().toString());

//        UserRole role = new UserRole();
//        User user = new User();
//        user.setUserName("dmitriy");
//        user.setPassword("221202");
//        role.setRole("ROLE_ADMIN");
//        user.setRoles(Set.of(role));
//        role.setUsers(Set.of(user));
//        userService.create(user);
//        userRolesRepository.save(role);
    }
}
