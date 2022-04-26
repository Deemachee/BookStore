package ru.learnup.bookstore.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.learnup.bookstore.dao.entity.Author;
import ru.learnup.bookstore.dao.entity.Warehouse;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookView {

    private Long id;

    private String title;

    private String imgUrl;

    private int year;

    private int count_page;

    private int price;

    private Author author;

    private Warehouse warehouse;

}
