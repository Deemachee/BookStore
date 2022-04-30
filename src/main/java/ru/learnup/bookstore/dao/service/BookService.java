package ru.learnup.bookstore.dao.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import ru.learnup.bookstore.BookStoreApplication;
import ru.learnup.bookstore.dao.entity.Author;
import ru.learnup.bookstore.dao.entity.Book;
import ru.learnup.bookstore.dao.entity.Warehouse;
import ru.learnup.bookstore.dao.repository.BookRepository;
import ru.learnup.bookstore.specifications.BookSpecification;

import javax.persistence.LockModeType;
import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class BookService {

    public static final Logger log = LoggerFactory.getLogger(BookStoreApplication.class);

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final WarehouseService warehouseService;

    public BookService(BookRepository bookRepository, AuthorService authorService, WarehouseService warehouseService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.warehouseService = warehouseService;
    }

    public List<Book> getBooks() {
        return bookRepository.getAll();
    }

    @Transactional
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBookById(long id) {
        return bookRepository.getById(id);
    }

    public Book findBookById(long id) {
        return bookRepository.findByIdQuery(id);
    }

    public List<Book> findByAuthor(String name) {
        return bookRepository.findByAuthor(name);
    }

    public Book findByBookTitle(String title) {
        return bookRepository.findByTitleQuery(title);
    }

    @Transactional
    @Lock(value = LockModeType.OPTIMISTIC)
    public Book update(Book book) {
        try {
            return bookRepository.save(book);
        } catch (OptimisticLockException e) {
            log.warn("Optimistic lock exception for post {}", book.getId());
            throw e;
        }
    }

    public List<Book> getBookBy(BookFilter filter) {
        Specification<Book> specification = where(BookSpecification.byFilter(filter));
       return bookRepository.findAll(specification);
    }

    @Transactional
    public void createBook(String title, String authorName, int quantity, String imgUrl, int countPage, int price, int year) {
        Book bookByFind = findByBookTitle(title);
        if (bookByFind == null) {
            Book book = new Book();
            Author author = authorService.findByAuthorName(authorName);
            if (author == null) {
                author = new Author();
                author.setName(authorName);
            }
            book.setAuthor(author);
            book.setCount_page(countPage);
            book.setImgUrl(imgUrl);
            book.setPrice(price);
            book.setTitle(title);
            book.setYear(year);
            Warehouse warehouse = new Warehouse();
            warehouse.setCount(quantity);
            book.setAuthor(author);
            book.setWarehouse(warehouse);
//            author.addBookToAuthor(book);
//            authorService.addAuthor(author);
            addBook(book);
            log.info("Книга " + book + " добавлена в магазин !");
        } else {
            log.info(" Книга \"" + title + "\" уже есть в магазине !");
        }
    }

    @Transactional
    public void createCatalog() {
        createBook("Токийский декаданс", "Рю Мураками", 50, "http://mywishlist.ru/pic/i/wish/orig/002/453/741.jpeg", 300, 400, 2018);
        createBook("Преступление и наказание", "Федор Достоевский", 70, "https://img.chaconne.ru/img/2037771_849884.jpg", 500, 350, 2013);
        createBook("Я и оно", "Зигмунд Фрэйд", 100, "https://cdn.ast.ru/v2/ASE000000000832154/COVER/cover1__w340.jpg", 400, 450, 2015);
        createBook("Женщина - не мужчина", "Итаф Рам", 30, "https://au-books.com/wp-content/uploads/2022/02/zhienshchina-nie_muzhchina.jpg", 600, 800, 2022);
        createBook("Война и мир", "Лев Толстой", 50, "https://bookprose.ru/pictures/books_covers/1010257708.jpg", 2000, 400, 2014);
        createBook("Идиот", "Федор Достоевский", 60, "https://top10a.ru/wp-content/uploads/2019/11/2-62.jpg", 400, 300, 2016);
        createBook("Анна Каренина", "Лев Толстой", 90, "https://static.my-shop.ru/product/3/359/3583905.jpg", 450, 250, 2012);
        createBook("Норвежский лес", "Харуки Мураками", 75, "https://book24.kz/upload/iblock/3bf/3bf41cd6ba7d57678653a5e2b3fa27ff.jpg", 350, 500, 2018);
    }

    @Transactional
    public Boolean deleteBook(Long id) {
        bookRepository.delete(getBookById(id));
        return true;
    }

}
