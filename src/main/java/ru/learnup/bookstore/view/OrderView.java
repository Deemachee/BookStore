package ru.learnup.bookstore.view;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.learnup.bookstore.dao.entity.Customer;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderView {

    private UserCartView customer;

    private int amount;

    private List<OrderDetailView> orderDetails;

    private Instant date;

    @Override
    public String toString() {
        return "[" +
                customer +
                ", сумма = " + amount +
                ", детали заказа " + orderDetails +
                ']';
    }
}
