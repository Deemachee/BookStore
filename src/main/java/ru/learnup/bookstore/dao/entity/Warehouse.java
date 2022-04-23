package ru.learnup.bookstore.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "warehouse")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "warehouse", cascade = CascadeType.ALL)
    private Book book;

    @Column(nullable = false)
    private int count;
}
