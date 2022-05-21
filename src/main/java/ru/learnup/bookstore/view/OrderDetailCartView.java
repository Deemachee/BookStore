package ru.learnup.bookstore.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailCartView {

    private BookCartView book;

    private int quantity;
}
