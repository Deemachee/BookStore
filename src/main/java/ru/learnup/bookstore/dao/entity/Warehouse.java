package ru.learnup.bookstore.dao.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "warehouse")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Warehouse implements Serializable {

    private static final long serialVersionUID = -7233678887165593315L;

    public Warehouse(int count) {
        this.count = count;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    @Valid
    @NotBlank
    private Long version;

    @Column(nullable = false)
    private int count;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Warehouse warehouse = (Warehouse) o;
        return id != null && Objects.equals(id, warehouse.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Warehouse{" +
                "count=" + count +
                '}';
    }
}
