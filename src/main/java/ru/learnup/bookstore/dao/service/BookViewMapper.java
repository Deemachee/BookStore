package ru.learnup.bookstore.dao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.learnup.bookstore.BookStoreApplication;
import ru.learnup.bookstore.dao.entity.Author;
import ru.learnup.bookstore.dao.entity.Book;
import ru.learnup.bookstore.dao.entity.Warehouse;
import ru.learnup.bookstore.view.BookView;

@Component
public class BookViewMapper {

    public static final Logger log = LoggerFactory.getLogger(BookStoreApplication.class);

    private final BookService bookService;

    private final AuthorService authorService;

    private final WarehouseService warehouseService;



    public BookViewMapper(BookService bookService, AuthorService authorService, WarehouseService warehouseService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.warehouseService = warehouseService;
    }

    public BookView mapBookToView(Book book) {
        BookView view = new BookView();
        view.setId(book.getId());
        view.setCount_page(book.getCount_page());
        view.setImgUrl(book.getImgUrl());
        view.setTitle(book.getTitle());
        view.setPrice(book.getPrice());
        view.setAuthor(new Author(book.getAuthor().getId(), book.getAuthor().getName()));
        view.setWarehouse(new Warehouse(book.getWarehouse().getId(), book.getWarehouse().getCount()));
        return view;
    }

    public Book mapBookFromView(BookView view) {
        Book bookByFind = bookService.findByBookTitle(view.getTitle());
        if (bookByFind == null) {
            Book book = new Book();
            Author author = authorService.findByAuthorName(view.getAuthor().getName());
            if (author == null) {
                author = new Author();
                author.setName(view.getAuthor().getName());
            }
            book.setCount_page(view.getCount_page());
            book.setImgUrl(view.getImgUrl());
            book.setPrice(view.getPrice());
            book.setTitle(view.getTitle());
            book.setYear(view.getYear());

//            book.setAuthor(view.getAuthor());
//            book.setWarehouse(view.getWarehouse());
            book.setAuthor(author);
            Warehouse warehouse = new Warehouse();
            warehouse.setCount(view.getWarehouse().getCount());
            book.setAuthor(author);
            book.setWarehouse(warehouse);
            log.info("Книга " + book + " добавлена в магазин !");
            return book;
        } else {
            log.info(" Книга \"" + view.getTitle() + "\" уже есть в магазине !");
            return null;
        }
    }
}
