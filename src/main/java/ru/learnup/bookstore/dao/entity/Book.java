package ru.learnup.bookstore.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class Book {

    public Book(Long id, String title, String imgUrl, int year, int count_page, int price, Author author) {
        this.id = id;
        this.title = title;
        this.imgUrl = imgUrl;
        this.year = year;
        this.count_page = count_page;
        this.price = price;
        this.author = author;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @Column
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
                ']';
    }
}
