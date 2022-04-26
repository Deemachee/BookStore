package ru.learnup.bookstore.dao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.learnup.bookstore.dao.entity.Book;
import ru.learnup.bookstore.dao.entity.Order;
import ru.learnup.bookstore.dao.entity.OrderDetail;
import ru.learnup.bookstore.dao.repository.OrderRepository;
import java.util.List;

@Service
public class OrderService {


    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Order addOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrderById(long id) {
        return orderRepository.getById(id);
    }

//    public Order findOrderById(long id) { return orderRepository.findByIdQuery(id);}



}
