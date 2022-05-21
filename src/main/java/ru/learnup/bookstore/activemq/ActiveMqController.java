package ru.learnup.bookstore.activemq;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.learnup.bookstore.dao.service.OrderService;
import ru.learnup.bookstore.view.OrderView;

import java.util.List;

@RestController
@RequestMapping("/active")
public class ActiveMqController {

    private final JmsTemplate jmsTemplate;

    private final OrderService orderService;

    public ActiveMqController(JmsTemplate jmsTemplate, OrderService orderService) {
        this.jmsTemplate = jmsTemplate;
        this.orderService = orderService;
    }

    @GetMapping( "/report/{login}")
    public List<OrderView> sendOrdersOfUser(@PathVariable("login") String login) {
        List<OrderView> findOrders = orderService.ordersOfCustomerFromMonth(login);
        String result = "Все заказы пользователя " + login + " за месяц: " + findOrders.toString();
        jmsTemplate.convertAndSend("report", result);
        return findOrders;
    }
}
