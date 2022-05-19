package ru.learnup.bookstore.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.learnup.bookstore.dao.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

    @Query(value = "select * from users where lower(user_name) = lower(:login);", nativeQuery = true)
    User findByLogin(String login);
}
