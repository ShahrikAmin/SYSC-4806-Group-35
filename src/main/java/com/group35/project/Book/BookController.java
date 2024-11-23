package com.group35.project.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/***
 * BookController class for handling HTTP requests for book operations available in BookService
 * @author Chibuzo Okpara v1
 */
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Create a new book
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return this.bookService.createBook(book);
    }

    // Retrieve a book by ID
    @GetMapping("/{id}")
    public Book findBookById(@PathVariable Long id) {
        return this.bookService.findBookById(id).orElseThrow(() ->
                new RuntimeException("Book not found with ID: " + id));
    }

    // Retrieve all books
    @GetMapping("/all")
    public Iterable<Book> getAllBooks() {
        return this.bookService.getAllBooks();
    }

    // Find books by title
    @GetMapping("/search")
    public List<Book> findBooksByTitle(@RequestParam String title) {
        return this.bookService.findBooksByTitle(title);
    }

    //Find books containing a word
    @GetMapping("/search/contains")
    public List<Book> findBooksByTitleContaining(@RequestParam String keyword) {
        return this.bookService.findBooksByTitleContaining(keyword);
    }

    // Find a book by ISBN
    @GetMapping("/search/isbn")
    public Book findBookByIsbn(@RequestParam String isbn) {
        return this.bookService.findBookByIsbn(isbn);
    }

    // Find books by author
    @GetMapping("/search/author")
    public List<Book> findBooksByAuthor(@RequestParam String author) {
        return this.bookService.findBooksByAuthor(author);
    }

    // Find books by publisher
    @GetMapping("/search/publisher")
    public List<Book> findBooksByPublisher(@RequestParam String publisher) {
        return this.bookService.findBooksByPublisher(publisher);
    }

    // Delete a book by ID
    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable Long id) {
        this.bookService.deleteBookById(id);
    }
}
