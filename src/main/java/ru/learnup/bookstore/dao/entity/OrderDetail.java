package ru.learnup.bookstore.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", nullable = false)
    private Order orderDetailOrder;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private int price;

}
