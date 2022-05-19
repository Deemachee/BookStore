package ru.learnup.bookstore.dao.mapper;

import org.springframework.stereotype.Component;
import ru.learnup.bookstore.dao.entity.Order;
import ru.learnup.bookstore.dao.entity.OrderDetail;
import ru.learnup.bookstore.dao.entity.Role;
import ru.learnup.bookstore.dao.entity.User;
import ru.learnup.bookstore.view.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class OrderViewMapper {

    public OrderView mapOrderToView(Order order) {

        OrderView view = new OrderView();
        view.setAmount(order.getAmount());
        view.setCustomer(new UserCartView(order.getCustomer().getUser().getUsername()));

        List<OrderDetail> orderDetails = order.getOrderDetails();
        List<OrderDetailView> ods = new ArrayList<>();
        OrderDetailView od = new OrderDetailView();

        for (OrderDetail orDet: orderDetails) {
            od.setBook(new BookCartView(orDet.getBook().getTitle()));
            od.setQuantity(orDet.getQuantity());
            od.setId(orDet.getId());
            ods.add(od);
        }

        view.setOrderDetails(ods);
        return view;
    }
}
