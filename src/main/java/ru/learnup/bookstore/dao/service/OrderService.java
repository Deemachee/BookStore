package ru.learnup.bookstore.dao.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.learnup.bookstore.dao.entity.Customer;
import ru.learnup.bookstore.dao.entity.Order;
import ru.learnup.bookstore.dao.entity.User;
import ru.learnup.bookstore.dao.mapper.OrderViewMapper;
import ru.learnup.bookstore.dao.repository.OrderRepository;
import ru.learnup.bookstore.view.OrderView;
import ru.learnup.bookstore.view.UserCartView;

import javax.persistence.EntityExistsException;
import javax.persistence.OptimisticLockException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderService {


    private final OrderRepository orderRepository;
    private final UserService userService;
    private final OrderViewMapper mapper;

    public OrderService(OrderRepository orderRepository, UserService userService, OrderViewMapper mapper) {
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.mapper = mapper;
    }

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }


    public Order addOrder(Order order) {
        try {
            return orderRepository.save(order);
        } catch (
                OptimisticLockException e) {
            log.warn("Optimistic lock exception for update order {}", order.getId());
            throw e;
        }
    }

    public Order getOrderById(long id) {
        return orderRepository.getById(id);
    }

    public Optional<Order> findOrderById(long id) {
        return orderRepository.findById(id);
    }

    public List<OrderView> ordersOfCustomerFromMonth(String login) {

        long current = Instant.now().getEpochSecond();
        long min = current - 2592000000L;

        User user = userService.findUserByLogin(login);
        if (user == null) {
            throw new EntityExistsException("orders with customer login "
                    + login + " are not exist");
        } else {

            OrderView view = new OrderView();
            List<Order> orders = getOrders();
            List<OrderView> findOrders = new ArrayList<>();

            for (Order or : orders) {
                if (or.getCustomer().getUser().getUsername().equals(login) &&
                        (or.getDate().getEpochSecond() > min)) {
                    view = mapper.mapOrderToView(or);
                    findOrders.add(view);
                }
            }
            return findOrders;
        }
    }


}
