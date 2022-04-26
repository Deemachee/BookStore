package ru.learnup.bookstore.dao.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "order_details_id")
//    private OrderDetail orderDetail;

//    @ManyToOne
//    @JoinColumn(name = "customer_id")
//    private Customer customer;

    @OneToMany(mappedBy = "orderIdInOrder")
    private List<BookInOrder> bookInOrders;

    @Override
    public String toString() {
        return "Order{" +
//                "orderDetail=" + orderDetail +
//                ", customer=" + customer +
                ", amount=" + amount +
                '}';
    }

    @Column
    private int amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return id != null && Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
