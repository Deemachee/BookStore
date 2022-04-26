package ru.learnup.bookstore.dao.service;

import org.springframework.stereotype.Service;
import ru.learnup.bookstore.dao.entity.Author;
import ru.learnup.bookstore.dao.repository.AuthorRepository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    private final AuthorRepository authorRepository;

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public void addAuthor(Author author) {
        authorRepository.save(author);
    }

    public Optional<Author> getAuthorById(long id) {
        return authorRepository.findById(id);
    }

    public Author findByAuthorName(String name) {
        return authorRepository.findByNameQuery(name);
    }

}
