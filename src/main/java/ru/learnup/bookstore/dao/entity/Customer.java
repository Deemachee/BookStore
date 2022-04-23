package ru.learnup.bookstore.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date birthdate;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "customer")
    private List<Order> orders;

    public void addOrderToCustomer(Order order) {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        orders.add(order);
        order.setCustomer(this);
    }
}
