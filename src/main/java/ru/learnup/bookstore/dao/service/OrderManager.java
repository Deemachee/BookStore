package ru.learnup.bookstore.dao.service;

import org.springframework.stereotype.Service;
import ru.learnup.bookstore.dao.entity.Book;
import ru.learnup.bookstore.dao.entity.BookInOrder;
import ru.learnup.bookstore.dao.entity.Order;
import ru.learnup.bookstore.dao.entity.OrderDetail;
import ru.learnup.bookstore.dao.repository.BookInOrderRepository;

@Service
public class OrderManager {

    public OrderManager(BookInOrderRepository bookInOrderRepository, BookService bookService, OrderService orderService) {
        this.bookInOrderRepository = bookInOrderRepository;
        this.bookService = bookService;
        this.orderService = orderService;
    }

    private final BookInOrderRepository bookInOrderRepository;

    private final BookService bookService;

    private final OrderService orderService;

//    public boolean addToOrder(Long bookId, Long orderId, int count) {
//        Book book = bookService.findBookById(bookId);
//        if (book == null) {return false;}
//        Order order = orderService.findOrderById(orderId);
//        if (order == null) {return false;}
//        BookInOrder  bookInOrder = new BookInOrder();
//        bookInOrder.setOrder(order);
//        bookInOrder.setBook(book);
//        bookInOrder.setQuantity(count);
//        bookInOrderRepository.save(bookInOrder);
//        return true;
//    }
}
