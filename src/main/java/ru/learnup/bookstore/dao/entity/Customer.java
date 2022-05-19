package ru.learnup.bookstore.dao.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {

    private static final long serialVersionUID = -4164880548957054817L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    public Customer(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

//    @OneToMany(mappedBy = "customer",
////            fetch = FetchType.EAGER,
//            cascade = CascadeType.PERSIST)
//    private List<Order> orders;

    @OneToOne(mappedBy = "customer",
            cascade = CascadeType.ALL)
    private User user;


    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", surname=" + surname +
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
