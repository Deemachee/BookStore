package ru.learnup.bookstore.controlers.rest;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.learnup.bookstore.dao.entity.Author;
import ru.learnup.bookstore.dao.service.*;
import ru.learnup.bookstore.view.AuthorView;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

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
    public Boolean createAuthor(@RequestBody AuthorView body) {
        Author author = authorService.findByAuthorName(body.getName());
        if (author != null) {
            throw new EntityExistsException(
                    String.format("Author with name = %s already exist", body.getName()));
        }
        Author createdAuthor = new Author();
        createdAuthor.setName(body.getName());
        authorService.addAuthor(createdAuthor);
        return true;
    }

    @Secured({"ROLE_ADMIN"})
    @PutMapping("/{authorId}")
    public Author updateAuthor(@PathVariable("authorId") Long authorId,
                               @RequestBody Author body) {
        if (body.getId() == null) {
            throw new EntityNotFoundException("Try to found null entity");
        }
        if (!Objects.equals(authorId, body.getId())) {
            throw new RuntimeException("Entity has bad Id");
        }
        Author author = authorService.findAuthorById(authorId);
        if (author == null) {
            throw new EntityNotFoundException("There are no authors with this id ");
        }
        if (!author.getName().equals(body.getName())) {
            author.setName(body.getName());
        }
        return authorService.update(author);
    }





}
