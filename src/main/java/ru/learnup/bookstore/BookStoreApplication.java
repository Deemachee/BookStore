package ru.learnup.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.learnup.bookstore.dao.service.*;


@SpringBootApplication
public class BookStoreApplication {

    public static final Logger log = LoggerFactory.getLogger(BookStoreApplication.class);

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(BookStoreApplication.class, args);

        AuthorService authorService = context.getBean(AuthorService.class);

        BookService bookService = context.getBean(BookService.class);

        WarehouseService warehouseService = context.getBean(WarehouseService.class);

        CustomerService customerService = context.getBean(CustomerService.class);

        OrderService orderService = context.getBean(OrderService.class);

        OrderDetailService orderDetailService = context.getBean(OrderDetailService.class);

        log.info("authors: {}", authorService.getAuthors());
        log.info("books: {}", bookService.getBooks());
        log.info("warehouse: {}", warehouseService.getWarehouse());
        log.info("customers: {}", customerService.getCustomers());
        log.info("orders: {}", orderService.getOrders());
        log.info("order details: {}", orderDetailService.getOrderDetail());

        String findAuthor = "Лев Толстой";
        log.info("Все книги автора {}: {}", findAuthor, bookService.findByAuthor(findAuthor));
    }
}
