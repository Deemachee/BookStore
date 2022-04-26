package ru.learnup.bookstore.dao.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Date birthdate;

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", birthdate=" + birthdate +
                '}';
    }

    //    @OneToMany(cascade = CascadeType.ALL,
//            fetch = FetchType.EAGER,
//            mappedBy = "customer")
//    private List<Order> orders;
//
//    public void addOrderToCustomer(Order order) {
//        if (orders == null) {
//            orders = new ArrayList<>();
//        }
//        orders.add(order);
//        order.setCustomer(this);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return id != null && Objects.equals(id, customer.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
