package ru.learnup.bookstore.controlers;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.learnup.bookstore.dao.entity.Book;
import ru.learnup.bookstore.dao.repository.BookRepository;
import ru.learnup.bookstore.dao.service.BookService;

import java.util.List;

@Controller
public class CatalogController {

    @Autowired
    private BookService bookService;

    @GetMapping("/catalog")
    public String catalog(Model model) {

        List<Book> books = bookService.getBooks();

        model.addAttribute("books", books);
        return "catalog";
    }


}
