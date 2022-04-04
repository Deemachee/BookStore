package ru.learnup.bookstore.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "authors")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true,
            nullable = false)
    private String authorName;

    @OneToMany(cascade = CascadeType.ALL,
            mappedBy = "authorBook")
    private List<Book> books;

    public void addBookToAuthor(Book book) {
        if (books == null) {
            books = new ArrayList<>();
        }
        books.add(book);
        book.setAuthorBook(this);
    }

}
