package ru.learnup.bookstore.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.learnup.bookstore.dao.user.UserRole;

import java.util.Set;

@Repository
public interface UserRolesRepository extends JpaRepository<UserRole, Long> {

    Set<UserRole> findByRoleIn(Set<String> roles);
}
