package ru.learnup.bookstore.controlers;

import org.springframework.web.bind.annotation.*;
import ru.learnup.bookstore.dao.entity.Book;
import ru.learnup.bookstore.dao.service.BookFilter;
import ru.learnup.bookstore.dao.service.BookService;
import ru.learnup.bookstore.dao.service.BookViewMapper;
import ru.learnup.bookstore.view.BookView;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/books")
public class BookControllerRest {

    private final BookService bookService;

    private final BookViewMapper bookViewMapper;

    public BookControllerRest(BookService bookService, BookViewMapper bookViewMapper) {
        this.bookService = bookService;
        this.bookViewMapper = bookViewMapper;
    }


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

    @PostMapping
    public BookView createBook(@RequestBody BookView body) {
        Book book = bookViewMapper.mapBookFromView(body);
        if (book == null) throw new EntityExistsException(
                String.format("Book with title = %s already exist", body.getTitle())
        );
        Book createdBook = bookService.addBook(book);
        return bookViewMapper.mapBookToView(createdBook);
    }

    @PutMapping("/{bookId}")
    public BookView updateBook(@PathVariable("bookId") Long bookId,
                               @RequestBody BookView body) {
        if (body.getId() == null) {
            throw new EntityNotFoundException(String.format("Try to found null entity"));
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

    @DeleteMapping("/{bookId}")
    public Boolean deleteBook(@PathVariable("bookId") Long bookId) {
        return bookService.deleteBook(bookId);
    }
}
