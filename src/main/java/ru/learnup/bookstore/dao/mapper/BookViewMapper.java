package ru.learnup.bookstore.dao.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.learnup.bookstore.BookStoreApplication;
import ru.learnup.bookstore.dao.entity.Author;
import ru.learnup.bookstore.dao.entity.Book;
import ru.learnup.bookstore.dao.entity.Warehouse;
import ru.learnup.bookstore.dao.service.AuthorService;
import ru.learnup.bookstore.view.AuthorView;
import ru.learnup.bookstore.view.BookView;
import ru.learnup.bookstore.view.WarehouseView;

@Component
public class BookViewMapper {

    public static final Logger log = LoggerFactory.getLogger(BookStoreApplication.class);

    private final AuthorService authorService;

    public BookViewMapper(AuthorService authorService) {
        this.authorService = authorService;
    }

    public BookView mapBookToView(Book book) {
        BookView view = new BookView();
        view.setId(book.getId());
        view.setCount_page(book.getCount_page());
        view.setYear(book.getYear());
        view.setImgUrl(book.getImgUrl());
        view.setTitle(book.getTitle());
        view.setPrice(book.getPrice());
        view.setAuthor(new AuthorView(book.getAuthor().getName()));
        view.setWarehouse(new WarehouseView(book.getWarehouse().getCount()));
        return view;
    }

    public Book mapBookFromView(BookView view) {
        Book book = new Book();
        book.setCount_page(view.getCount_page());
        book.setImgUrl(view.getImgUrl());
        book.setPrice(view.getPrice());
        book.setTitle(view.getTitle());
        book.setYear(view.getYear());
        Author author = authorService.findByAuthorName(view.getAuthor().getName());
        if (author == null) {
            author = new Author();
            author.setName(view.getAuthor().getName());
        }
        book.setAuthor(author);
        book.setWarehouse(new Warehouse(view.getWarehouse().getCount()));
        return book;
    }
}
