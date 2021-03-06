package ru.learnup.bookstore.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCartView {

    private String login;

    @Override
    public String toString() {
        return "[" +
                "пользователь = " + login + '\'' +
                ']';
    }
}
