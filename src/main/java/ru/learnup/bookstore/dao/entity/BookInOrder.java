package ru.learnup.bookstore.dao.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "book_in_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookInOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Book bookInOrder;

    @ManyToOne
    private Order orderIdInOrder;

    @Column
    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BookInOrder that = (BookInOrder) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
