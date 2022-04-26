package ru.learnup.bookstore.dao.repository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.learnup.bookstore.dao.entity.Author;
import ru.learnup.bookstore.dao.entity.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value = "from Book b " +
            "join fetch b.author where b.author.name = :name")
    List<Book> findByAuthor(String name);

    @Query(value = "from Book b join fetch b.author ORDER BY b.id")
    List<Book> getAll();

    @Query(value = "from Book b join fetch b.author where b.id = :id")
    Book findByIdQuery(long id);

    List<Book> findAllByTitle(String title);

    @Query(value = "select * from books where lower(title) = lower(:titleBook);", nativeQuery = true)
    Book findByTitleQuery(String titleBook);

    List<Book> findAll(Specification<Book> specification);
}
