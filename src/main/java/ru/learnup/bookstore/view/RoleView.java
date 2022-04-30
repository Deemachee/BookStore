package ru.learnup.bookstore.view;

import lombok.*;
import ru.learnup.bookstore.dao.user.Role;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleView {

    private String role;

    public RoleView(Role role) {
        this.role = role.getRole();
    }
}
