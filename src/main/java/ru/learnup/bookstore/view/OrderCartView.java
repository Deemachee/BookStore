package ru.learnup.bookstore.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderCartView {

    private UserCartView customer;

    private List<OrderDetailView> orderDetails;
}
