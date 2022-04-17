package ru.learnup.bookstore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.learnup.bookstore.dao.entity.Product;
import ru.learnup.bookstore.dao.service.*;


@SpringBootApplication
public class BookStoreApplication {

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

        log.info("Product 1: {}", productService.findProductById(5L));

        log.info("authors: {}", authorService.getAuthors());
        log.info("books: {}", bookService.getBooks());
        log.info("warehouse: {}", warehouseService.getWarehouse());
        log.info("customers: {}", customerService.getCustomers());
        log.info("orders: {}", orderService.getOrders());
        log.info("order details: {}", orderDetailService.getOrderDetail());

        String findAuthor = "Лев Толстой";
        log.info("Все книги автора {}: {}", findAuthor, bookService.findByAuthor(findAuthor));

        Product product = productService.findProductById(6L).orElseThrow(Exception::new);
        log.info("Текущее состояние продукта: {}", product.toString());
        product.setText("Text " + product.getVersion());
        productService.updateProduct(product);
        Product product1 = productService.findProductById(6L).orElseThrow(Exception::new);
        log.info("Cостояние продукта после апдэйта: {}", product1.toString());
    }
}
