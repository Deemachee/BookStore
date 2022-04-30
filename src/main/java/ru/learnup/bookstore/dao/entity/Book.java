package ru.learnup.bookstore.dao.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {

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

//    @OneToMany(mappedBy = "bookInOrder")
//    private List<BookInOrder> bookInOrders;

    @Column
    private int price;

    @ManyToOne(cascade = {CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH},
                fetch = FetchType.LAZY)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Book book = (Book) o;
        return id != null && Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
