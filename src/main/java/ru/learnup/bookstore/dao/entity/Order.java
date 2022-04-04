package ru.learnup.bookstore.dao.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private int amount;

    @OneToOne(mappedBy = "orderDetailOrder",
            cascade = CascadeType.ALL)
    private OrderDetail orderDetail;
}
