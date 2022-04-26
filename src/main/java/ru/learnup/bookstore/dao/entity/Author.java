package ru.learnup.bookstore.dao.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "authors")
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "author_name")
    private String name;

//    @OneToMany(cascade = CascadeType.ALL,
//            mappedBy = "author")
//    @ToString.Exclude
//    private List<Book> books;

    public Author(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Author(String name) {
        this.name = name;
    }


//    public void addBookToAuthor(Book book) {
//        if (books == null) {
//            books = new ArrayList<>();
//        }
//        books.add(book);
//        book.setAuthor(this);
//    }

    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Author author = (Author) o;
        return id != null && Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
