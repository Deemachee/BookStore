package ru.learnup.bookstore.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "author_name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "author")
    private List<Book> books;

    public Author(String name) {
        this.name = name;
    }

    public void addBookToAuthor(Book book) {
        if (books == null) {
            books = new ArrayList<>();
        }
        books.add(book);
        book.setAuthor(this);
    }
}
