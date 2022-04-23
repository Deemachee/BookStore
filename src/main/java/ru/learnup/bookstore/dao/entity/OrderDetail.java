package ru.learnup.bookstore.dao.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "order_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "orderDetail", cascade = CascadeType.ALL)
    private Order order;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private int price;

    @OneToMany
    @JoinColumn(name = "order_detail_id")
    private List<Book> bookList;

//    @OneToMany(mappedBy = "orderDetail",
//            fetch = FetchType.EAGER)
//    @Fetch(FetchMode.JOIN)
//    private List<Book> bookList;

}
