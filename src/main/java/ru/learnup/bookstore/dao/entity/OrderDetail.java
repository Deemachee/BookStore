package ru.learnup.bookstore.dao.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne(mappedBy = "orderDetail", cascade = CascadeType.ALL)
//    private Order order;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private int price;


//    @OneToMany
//    @JoinColumn(name = "order_detail_id")
//    private List<Book> bookList;

//    @OneToMany(mappedBy = "orderDetail",
//            fetch = FetchType.EAGER)
//    @Fetch(FetchMode.JOIN)
//    private List<Book> bookList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderDetail that = (OrderDetail) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "count=" + count +
                ", price=" + price +
                '}';
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
