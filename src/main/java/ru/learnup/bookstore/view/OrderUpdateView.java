package ru.learnup.bookstore.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateView {

    private Long id;

    private UserCartView customer;

    private List<OrderDetailCartView> orderDetails;
}
