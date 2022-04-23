package ru.learnup.bookstore.dao.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_details_id")
    private OrderDetail orderDetail;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column
    private int amount;

}
