package ru.learnup.bookstore.controlers.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.learnup.bookstore.BookStoreApplication;
import ru.learnup.bookstore.dao.entity.Book;
import ru.learnup.bookstore.dao.service.BookFilter;
import ru.learnup.bookstore.dao.service.BookService;
import ru.learnup.bookstore.dao.mapper.BookViewMapper;
import ru.learnup.bookstore.view.BookView;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/books")
public class BookController {

    public static final Logger log = LoggerFactory.getLogger(BookStoreApplication.class);

    private final BookService bookService;

    private final BookViewMapper bookViewMapper;

    public BookController(BookService bookService, BookViewMapper bookViewMapper) {
        this.bookService = bookService;
        this.bookViewMapper = bookViewMapper;
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping
    public List<BookView> getBooks(
            @RequestParam(value = "title", required = false) String title
    ) {
        return bookService.getBookBy(new BookFilter(title))
                .stream()
                .map(bookViewMapper::mapBookToView).
                collect(Collectors.toList());
    }

    @GetMapping("/{bookId}")
    public BookView getBookById(@PathVariable("bookId") Long bookId) {
        return bookViewMapper.mapBookToView(bookService.findBookById(bookId));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public BookView createBook(@RequestBody BookView body) {

        Book book = bookViewMapper.mapBookFromView(body);
        Book findBook = bookService.findByBookTitle(book.getTitle());

        if (findBook != null) throw new EntityExistsException(
                String.format("Book with title = %s already exist", body.getTitle())
        );

        Book createdBook = bookService.addBook(book);
        log.info("Книга <<" + book.getTitle() + ">> добавлена в магазин !");
        return bookViewMapper.mapBookToView(createdBook);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping("/{bookId}")
    public BookView updateBook(@PathVariable("bookId") Long bookId,
                               @RequestBody BookView body) {
        if (body.getId() == null) {
            throw new EntityNotFoundException("Try to found null entity");
        }
        if (!Objects.equals(bookId, body.getId())) {
            throw new RuntimeException("Entity has bad Id");
        }
        Book book = bookService.findBookById(bookId);
        if (!book.getTitle().equals(body.getTitle())) {
            book.setTitle(body.getTitle());
        }
        if (book.getCount_page() != (body.getCount_page())) {
            book.setCount_page(body.getCount_page());
        }
        if (book.getPrice() != (body.getPrice())) {
            book.setPrice(body.getPrice());
        }
        if (!book.getImgUrl().equals(body.getImgUrl())) {
            book.setImgUrl(body.getImgUrl());
        }
        if (book.getYear() != (body.getYear())) {
            book.setYear(body.getYear());
        }
        Book updated = bookService.update(book);
        return bookViewMapper.mapBookToView(updated);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{bookId}")
    public Boolean deleteBook(@PathVariable("bookId") Long bookId) {
        return bookService.deleteBook(bookId);
    }
}
