package ru.learnup.bookstore.controlers.rest;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.learnup.bookstore.dao.entity.Author;
import ru.learnup.bookstore.dao.entity.Book;
import ru.learnup.bookstore.dao.service.*;
import ru.learnup.bookstore.dao.user.User;
import ru.learnup.bookstore.view.AuthorView;
import ru.learnup.bookstore.view.BookView;

import javax.persistence.EntityExistsException;
import java.util.List;

@RestController
@RequestMapping("api/authors")
public class AuthorController {

    private final AuthorService authorService;


    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping
    public List<Author> getAuthors() {
        return authorService.getAuthors();
    }


    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{authorId}")
    public Boolean deleteUser(@PathVariable("authorId") Long authorId) {
        authorService.deleteAuthor(authorId);
        return true;
    }

    @Secured("ROLE_ADMIN")
    @PostMapping
    public Author createAuthor(@RequestBody AuthorView body) {
        Author author = authorService.findByAuthorName(body.getName());
        if (author != null) {
            throw new EntityExistsException(
                    String.format("Author with name = %s already exist", body.getName()));
        } else

        return authorService.addAuthor(author);
    }






}
