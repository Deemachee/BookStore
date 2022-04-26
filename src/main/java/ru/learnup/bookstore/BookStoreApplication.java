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
import ru.learnup.bookstore.dao.service.*;

import java.util.List;


@SpringBootApplication
public class BookStoreApplication extends SpringBootServletInitializer {

    public static final Logger log = LoggerFactory.getLogger(BookStoreApplication.class);

    public static void main(String[] args) throws Exception {

        ConfigurableApplicationContext context = SpringApplication.run(BookStoreApplication.class, args);

        AuthorService authorService = context.getBean(AuthorService.class);

        BookService bookService = context.getBean(BookService.class);

        WarehouseService warehouseService = context.getBean(WarehouseService.class);

        CustomerService customerService = context.getBean(CustomerService.class);

        OrderService orderService = context.getBean(OrderService.class);

        OrderDetailService orderDetailService = context.getBean(OrderDetailService.class);

        ProductService productService = context.getBean(ProductService.class);

        bookService.createCatalog();

        log.info("Product 1: {}", productService.findProductById(5L));

        log.info("books: {}", bookService.getBooks());

        String findAuthor = "Лев Толстой";
        log.info("Все книги автора {}: {}", findAuthor, bookService.findByAuthor(findAuthor));

        Product product = productService.findProductById(1L).orElseThrow(Exception::new);
        log.info("Текущее состояние продукта: {}", product.toString());
        product.setText("Text " + product.getVersion());
        productService.updateProduct(product);
        Product product1 = productService.findProductById(1L).orElseThrow(Exception::new);
        product1.setText("Text " + product1.getVersion());
        log.info("Cостояние продукта после апдэйта: {}", product1.toString());

    }
}
