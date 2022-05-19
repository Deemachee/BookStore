package ru.learnup.bookstore.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerView {

    private UserCartView user;

    private String name;

    private String surname;

}
