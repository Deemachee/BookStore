package ru.learnup.bookstore.controlers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.learnup.bookstore.dao.entity.Author;
import ru.learnup.bookstore.dao.service.*;
import ru.learnup.bookstore.view.AuthorView;
import ru.learnup.bookstore.view.BookView;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/authors")
public class AuthorControllerRest {

        private final AuthorService authorService;


    public AuthorControllerRest(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
        public List<Author> getAuthors() {
            return authorService.getAuthors();
        }

}
