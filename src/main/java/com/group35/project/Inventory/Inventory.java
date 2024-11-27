package com.group35.project.Inventory;

import com.group35.project.Book.BookRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.group35.project.Book.Book;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@AllArgsConstructor

@Entity

public class Inventory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int size;

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "inventory_id")
    private Map<Long, Book> books;
    //private List<Book> bookInventory = new ArrayList<Book>();


    @ElementCollection
    @CollectionTable(name = "inventory_stock", joinColumns = @JoinColumn(name = "inventory_id"))
    @MapKeyColumn(name = "book_id")
    private Map<Long, Integer> stock;



    public Inventory() {
        this.stock = new HashMap<>();
        this.books = new HashMap<>();
        this.size = 0;
    }

    public void addBook(Book book) {
        if (book.getId() == null) {
            throw new IllegalArgumentException("Book must be persisted before adding to inventory");
        }

        Long bookId = book.getId();
        books.put(bookId, book);
        stock.put(bookId, stock.getOrDefault(bookId, 0) + 1);
    }

    public boolean removeBook(Long bookId) {
        if (books.containsKey(bookId)) {
            books.remove(bookId);
            stock.remove(bookId);
            return true;
        }
        return false;
    }

    public boolean removeBookByISBN(String isbn) {
        for (Map.Entry<Long, Book> entry : books.entrySet()) {
            if (entry.getValue().getIsbn().equals(isbn)) {
                return removeBook(entry.getKey());
            }
        }
        return false; // Book with the given ISBN not found
    }

    public Book getBook(Long bookId) {
        return books.get(bookId);
    }

    public Book getBookByISBN(String isbn) {
        for(Book book : books.values()){
            if (book.getIsbn().equals(isbn)){
                return book;
            }
        }
        return null;
    }

    public boolean hasBook(String isbn) {
        for (Book b: this.books.values()) {
            if (b.getIsbn().equals(isbn)){
                return true;
            }
        }
        return false;
    }

    public boolean decreaseStock(Long bookId) {
        if (stock.containsKey(bookId) && stock.get(bookId) > 0) {
            stock.put(bookId, stock.get(bookId) - 1);
            return true;
        }
        return false;
    }

    public boolean increaseStock(Long bookId, int amount) {
        if (books.containsKey(bookId)) {
            stock.put(bookId, stock.getOrDefault(bookId, 0) + amount);
            return true;
        }
        return false;
    }

    public void printInventory() {
        System.out.println("Inventory:");
        for (Long bookId : books.keySet()) {
            Book book = books.get(bookId);
            int count = stock.getOrDefault(bookId, 0);
            System.out.println(book + " | Stock: " + count);
        }
    }

    public Long getId() {
        return id;
    }

    public int getSize() {
        return books.size();
    }

    public Map<Long, Book> getAllBooks() {
        return books;
    }

    public Map<Long, Integer> getStock() {
        return stock;
    }

    public int getQuantity(Long id){
        return stock.get(id);
    }

    public void setSize(int size){
        this.size = size;
    }

    @Override
    public String toString() {
        StringBuilder booksDetails = new StringBuilder();

        if (books != null && !books.isEmpty()) {
            booksDetails.append("{");
            for (Map.Entry<Long, Book> entry : books.entrySet()) {
                Long bookId = entry.getKey();
                Book book = entry.getValue();
                booksDetails.append("ID: ").append(bookId).append(" -> ").append(book.toString()).append(", ");
            }
            if (booksDetails.length() > 1) {
                booksDetails.setLength(booksDetails.length() - 2); // Remove the trailing comma and space
            }
            booksDetails.append("}");
        } else {
            booksDetails.append("{}"); // Empty map
        }

        System.out.println("Inventory{" +
                "id=" + id +
                ", size=" + size +
                ", books=" + booksDetails +
                '}');

        return "Inventory{" +
                "id=" + id +
                ", size=" + size +
                ", books=" + booksDetails +
                '}';
    }
}
