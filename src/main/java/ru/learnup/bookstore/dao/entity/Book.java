package ru.learnup.bookstore.dao.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @Column(name = "img_url")
    private String imgUrl;

    @Column
    private int year;

    @Column
    private int count_page;

    @Column
    private int price;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    public Book(String title, String imgUrl, int year, int count_page, int price, Author author, Warehouse warehouse) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.year = year;
        this.count_page = count_page;
        this.price = price;
        this.author = author;
        this.warehouse = warehouse;
    }

    @Override
    public String toString() {
        return "Book [" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", year=" + year +
                ", count_page=" + count_page +
                ", price=" + price +
                ", author=" + author.getName() +
                ", count_book=" + warehouse.getCount() +
                ']';
    }
}
