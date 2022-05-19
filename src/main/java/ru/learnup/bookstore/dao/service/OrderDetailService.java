package ru.learnup.bookstore.dao.service;

import org.springframework.stereotype.Service;
import ru.learnup.bookstore.dao.entity.OrderDetail;
import ru.learnup.bookstore.dao.repository.OrderDetailRepository;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {

    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }
    private final OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> getOrderDetail() {
        return orderDetailRepository.findAll();
    }

    public OrderDetail addOrderDetail(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    public OrderDetail getOrderDetailById(long id) {
        return orderDetailRepository.getById(id);
    }

    public Boolean removeOrderDetailById(long id) {
        orderDetailRepository.delete(getOrderDetailById(id));
        return true;
    }

    public Optional<OrderDetail> findOrderDetailById (long id) {
        return orderDetailRepository.findById(id);
    }
}
