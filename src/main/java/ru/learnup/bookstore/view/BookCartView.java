package ru.learnup.bookstore.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCartView {

    private String title;

    @Override
    public String toString() {
        return "[" +
                "title = '" + title + '\'' +
                ']';
    }

}
