package ru.learnup.bookstore.dao.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "books")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true,
            nullable = false)
    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id",
            nullable = false)
    private Author authorBook;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private int count_page;

    @Column(nullable = false)
    private int price;

    @OneToOne(mappedBy = "bookWarehouse",
            cascade = CascadeType.ALL)
    private Warehouse warehouse;

}
