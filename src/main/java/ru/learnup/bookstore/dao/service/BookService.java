package ru.learnup.bookstore.dao.service;

import org.springframework.stereotype.Service;
import ru.learnup.bookstore.dao.entity.Author;
import ru.learnup.bookstore.dao.entity.Book;
import ru.learnup.bookstore.dao.repository.BookRepository;
import java.util.List;

@Service
public class BookService {

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private final BookRepository bookRepository;

    public List<Book> getBooks() {
        return bookRepository.getAll();
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBookById(long id) {
        return bookRepository.getById(id);
    }

    public List<Book> findByAuthor(String name) {
        return bookRepository.findByAuthor(name);
    }

}
