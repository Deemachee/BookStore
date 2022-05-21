package ru.learnup.bookstore.controlers.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.learnup.bookstore.dao.entity.*;
import ru.learnup.bookstore.dao.mapper.OrderViewMapper;
import ru.learnup.bookstore.dao.service.*;
import ru.learnup.bookstore.view.*;

import javax.persistence.EntityNotFoundException;
import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.security.Principal;
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
    @PostMapping
    public Boolean createOrder(@RequestBody OrderCartView body) {

        Order entity = new Order();
        User user = userService.findUserByLogin(body.getCustomer().getLogin());
        if (user == null) {
            throw new EntityNotFoundException("There are no users with this login ");
        }

        entity.setCustomer(user.getCustomer());

        int amount = 0;
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailCartView od : body.getOrderDetails()
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
        return true;
    }

    @PreAuthorize("#user.name == authentication.principal.username")
    @PutMapping("/{orderId}")
    public OrderView updateOrder(@PathVariable("orderId") Long orderId,
                                 Principal user,
                                 @RequestBody OrderUpdateView body) {
        if (body.getId() == null) {
            throw new EntityNotFoundException("Try to found null entity");
        }
        if (!Objects.equals(orderId, body.getId())) {
            throw new RuntimeException("Entity has bad id");
        }
        Order order = orderService.findOrderById(orderId).orElse(null);
        if (order == null) {
            throw new EntityNotFoundException("There are no order with this id ");
        }

        if (!body.getCustomer().getLogin().equals(user.getName())) {
            throw new AccessDeniedException("This is not your order");
        }

        int amount = order.getAmount();

        int size = body.getOrderDetails().size();

        List<OrderDetail> updated = new ArrayList<>();
        List<OrderDetailView> detailViews = new ArrayList<>();

        for (int i = 0; i < size; i++) {

            OrderDetailCartView odView = body.getOrderDetails().get(i);
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

    @GetMapping
    @PreAuthorize("#user.name == authentication.principal.username")
    public List<OrderView> getOrders(Principal user) {

        List<Order> allOrders = orderService.getOrders();
        List<OrderView> orders = new ArrayList<>();
        OrderView order = new OrderView();

        User currentUser = userService.findUserByLogin(user.getName());

        Boolean isAdmin = currentUser.getRoles().stream()
                .map(Role::getRole)
                .anyMatch(r -> r.equals("ROLE_ADMIN"));

        for (Order od : allOrders) {
            if (od.getCustomer().getUser().getUsername().equals(currentUser.getUsername()) ||
                    isAdmin) {
                order = orderViewMapper.mapOrderToView(od);
                orders.add(order);
            }
        }
        return orders;
    }


}
