package com.group35.project.Book;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/***
 * BookService class for logic and book operations.
 * @author Chibuzo Okpara
 */
@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //Create a new book
    public Book createBook(Book book){
        return this.bookRepository.save(book);
    }

    //find a book by isbn
    public Book findBookByIsbn(String isbn){
        return this.bookRepository.findByIsbn(isbn);
    }

    //find books by an author
    public List<Book> findBooksByAuthor(String author){
        return this.bookRepository.findByAuthor(author);
    }

    //find books by a publisher
    public List<Book> findBooksByPublisher(String publisher){
        return this.bookRepository.findByPublisher(publisher);
    }

    //Find a book by ID
    public Optional<Book> findBookById(Long id) {
        return this.bookRepository.findById(id);
    }

    // Find Books by Title
    public List<Book> findBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    //Find books by a word in the title
    public List<Book> findBooksByTitleContaining(String keyword) {
        return bookRepository.findByTitleContaining(keyword);
    }

    //Return all books
    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Delete a Book by ID
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }


}
