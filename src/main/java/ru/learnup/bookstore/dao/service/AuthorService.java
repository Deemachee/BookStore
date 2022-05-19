package ru.learnup.bookstore.dao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import ru.learnup.bookstore.BookStoreApplication;
import ru.learnup.bookstore.dao.entity.Author;
import ru.learnup.bookstore.dao.repository.AuthorRepository;

import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class AuthorService {

    public static final Logger log = LoggerFactory.getLogger(BookStoreApplication.class);

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    private final AuthorRepository authorRepository;

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public Author addAuthor(Author author) {
        return authorRepository.save(author);
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    public Author getAuthorById(long id) {
        return authorRepository.findByIdQuery(id);
    }

    public Author findAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    public Author findByAuthorName(String name) {
        return authorRepository.findByNameQuery(name);
    }

    @Transactional
    @Lock(value = LockModeType.OPTIMISTIC)
    public Author update(Author author) {
        try {
            return authorRepository.save(author);
        } catch (OptimisticLockException e) {
            log.warn("Optimistic lock exception for update author {}", author.getId());
            throw e;
        }
    }

}
