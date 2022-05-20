package ru.learnup.bookstore.activemq;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.learnup.bookstore.dao.service.OrderService;

@RestController
@RequestMapping("/active")
public class ActiveMqController {

    private final JmsTemplate jmsTemplate;

    private final OrderService orderService;

    public ActiveMqController(JmsTemplate jmsTemplate, OrderService orderService) {
        this.jmsTemplate = jmsTemplate;
        this.orderService = orderService;
    }

    @GetMapping(value = "/report/{login}", produces = "text/html")
    public String sendOrdersOfUser(@PathVariable("login") String login) {
        String result = orderService.ordersOfCustomerFromMonth(login);
        jmsTemplate.convertAndSend("report", result);
        return "done";
    }
}
