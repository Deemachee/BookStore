package ru.learnup.bookstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.learnup.bookstore.dao.entity.Customer;
import ru.learnup.bookstore.dao.entity.Role;
import ru.learnup.bookstore.dao.entity.User;
import ru.learnup.bookstore.dao.service.BookService;
import ru.learnup.bookstore.dao.service.UserService;

import java.util.Set;


@SpringBootApplication
public class BookStoreApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {

        ConfigurableApplicationContext context = SpringApplication.run(BookStoreApplication.class, args);

    }
}
