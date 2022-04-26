package ru.learnup.bookstore.dao.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String text;

    @Version
    @Valid
    @NotBlank
    private Long version;

}
