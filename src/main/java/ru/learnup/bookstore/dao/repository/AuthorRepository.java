package ru.learnup.bookstore.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.learnup.bookstore.dao.entity.Author;
import ru.learnup.bookstore.dao.entity.Book;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(value = "select * from authors where lower(author_name) = lower(:name);", nativeQuery = true)
    Author findByNameQuery(String name);

    @Query(value = "from Author a where a.id = :id")
    Author findByIdQuery(long id);
}
