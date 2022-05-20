package ru.learnup.bookstore.controlers.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.learnup.bookstore.dao.entity.*;
import ru.learnup.bookstore.dao.mapper.OrderViewMapper;
import ru.learnup.bookstore.dao.service.*;
import ru.learnup.bookstore.view.*;

import javax.persistence.EntityNotFoundException;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final UserService userService;
    private final OrderService orderService;
    private final BookService bookService;
    private final OrderDetailService orderDetailService;
    private final WarehouseService warehouseService;
    private final OrderViewMapper orderViewMapper;

    public OrderController(UserService userService, OrderService orderService, BookService bookService,
                           OrderDetailService orderDetailService, WarehouseService warehouseService,
                           OrderViewMapper orderViewMapper) {
        this.userService = userService;
        this.orderService = orderService;
        this.bookService = bookService;
        this.orderDetailService = orderDetailService;
        this.warehouseService = warehouseService;
        this.orderViewMapper = orderViewMapper;
    }


    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @Transactional
    @Lock(value = LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    @PostMapping
    public Boolean createOrder(@RequestBody OrderCartView body) {
        try {
            Order entity = new Order();
            User user = userService.findUserByLogin(body.getCustomer().getLogin());
            if (user == null) {
                throw new EntityNotFoundException("There are no users with this login ");
            }

            entity.setCustomer(user.getCustomer());

            int amount = 0;
            List<OrderDetail> orderDetails = new ArrayList<>();
            for (OrderDetailView od : body.getOrderDetails()
            ) {
                OrderDetail detail = new OrderDetail();
                Book book = bookService.findByBookTitle(od.getBook().getTitle());
                if (book == null) {
                    throw new EntityNotFoundException("There are no books with this title ");
                }
                detail.setBook(book);
                if (od.getQuantity() > book.getWarehouse().getCount()) {
                    throw new RuntimeException(String.format("Книга <{}> отсутствует на складе в необходимом количестве!", book.getTitle()));
                } else {
                    detail.setQuantity(od.getQuantity());
                    orderDetails.add(detail);
                    entity.addOrderDetailToOrder(detail);

                    amount += book.getPrice() * od.getQuantity();
                    Warehouse warehouse = book.getWarehouse();
                    int warehouseQuantity = warehouse.getCount();
                    warehouseQuantity -= od.getQuantity();
                    warehouse.setCount(warehouseQuantity);
                    warehouseService.updateWarehouse(warehouse);
                    orderDetailService.addOrderDetail(detail);
                }
            }

            entity.setOrderDetails(orderDetails);
            entity.setAmount(amount);
            entity.setDate(Instant.now());
            orderService.addOrder(entity);
        } catch (OptimisticLockException e) {
            log.warn("Книга отсутствует на складе в необходимом количестве!");
        }
        return true;
    }

    @PreAuthorize("#login == authentication.principal.username")
    @PutMapping("/{orderId}, {login}")
    public OrderView updateOrder(@PathVariable("orderId") Long orderId,
                                 @PathVariable("login") String login,
                                 @RequestBody OrderCartView body) {
        if (body.getCustomer().getLogin() == null) {
            throw new EntityNotFoundException("Try to found null entity");
        }
        if (!Objects.equals(login, body.getCustomer().getLogin())) {
            throw new RuntimeException("Entity has bad login");
        }
        Order order = orderService.findOrderById(orderId).orElse(null);
        if (order == null) {
            throw new EntityNotFoundException("There are no order with this id ");
        }

        int amount = order.getAmount();

        int size = body.getOrderDetails().size();

        List<OrderDetail> updated = new ArrayList<>();
        List<OrderDetailView> detailViews = new ArrayList<>();

        for (int i = 0; i < size; i++) {

            OrderDetailView odView = body.getOrderDetails().get(i);
            OrderDetail od = order.getOrderDetails().get(i);

            Book view = bookService.findByBookTitle(odView.getBook().getTitle());
            Book book = od.getBook();

            Warehouse whBook = book.getWarehouse();
            int qBook = whBook.getCount();
            qBook += od.getQuantity();
            whBook.setCount(qBook);
            warehouseService.updateWarehouse(whBook);

            Warehouse whView = view.getWarehouse();
            int qView = whView.getCount();
            qView += odView.getQuantity();
            whView.setCount(qView);
            warehouseService.updateWarehouse(whView);

            amount -= od.getBook().getPrice() * od.getQuantity();

            od.setBook(view);
            od.setQuantity(odView.getQuantity());

            amount += od.getBook().getPrice() * od.getQuantity();

            updated.add(od);
            orderDetailService.addOrderDetail(od);
        }
        order.setOrderDetails(updated);
        order.setAmount(amount);
        Order updatedOrder = orderService.addOrder(order);

        return orderViewMapper.mapOrderToView(updatedOrder);
    }

}
