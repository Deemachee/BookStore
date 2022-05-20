package ru.learnup.bookstore.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailView {

    private Long id;

    private BookCartView book;

    private int quantity;

    @Override
    public String toString() {
        return "[" +
                "id = " + id +
                ", book" + book +
                ", quantity = " + quantity +
                ']';
    }
}
