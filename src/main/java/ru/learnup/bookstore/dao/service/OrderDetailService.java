package ru.learnup.bookstore.dao.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import ru.learnup.bookstore.dao.entity.OrderDetail;
import ru.learnup.bookstore.dao.repository.OrderDetailRepository;

import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrderDetailService {

    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }
    private final OrderDetailRepository orderDetailRepository;

    public List<OrderDetail> getOrderDetail() {
        return orderDetailRepository.findAll();
    }

    @Transactional
    @Lock(value = LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    public OrderDetail addOrderDetail(OrderDetail orderDetail) {
        try {
        return orderDetailRepository.save(orderDetail);
        } catch (
                OptimisticLockException e) {
            log.warn("Optimistic lock exception for update orderDetail {}", orderDetail.getId());
            throw e;
        }
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
