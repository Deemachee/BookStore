package ru.learnup.bookstore.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.learnup.bookstore.dao.user.Role;

import java.util.List;
import java.util.Set;

@Repository
public interface RolesRepository extends JpaRepository<Role, Long> {

    Set<Role> findByRoleIn(Set<String> roles);

    List<Role> findAll();

    Role findByRole(String role);
}
