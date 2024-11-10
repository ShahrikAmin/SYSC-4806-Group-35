package com.group35.project.Inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.print.Book;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity

public class Inventory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int size;

    @OneToMany
    private List<Book> bookInventory = new ArrayList<>(Book);

    public Inventory(long id) {
        this.id = id;
        this.size = 0;
    }

    public void addBook(Book book) {
        if (bookInventory ==null) bookInventory = new ArrayList<>();
        bookInventory.add(book);
        this.size++;
    }

    public void removeBook(Book book) {bookInventory.remove(book);
    this.size--;}

    public void printBooks() {
        for (Book book : bookInventory) {
            System.out.println(book.toString());
        }
    }

    public void printInventory() {
        if (bookInventory.isEmpty()) {
            System.out.println("Inventory is empty");
        }else {
            for (Book book : bookInventory) {
                System.out.println(book);
            }
        }
    }


    public void removeBookWithId(Long bookId) {
        bookInventory.removeIf(book -> book.getId().equals(bookId));
        this.size = bookInventory.size();
    }

    public void removeBookWithISBN(String isbn) {
        bookInventory.removeIf(book -> book.getIsbn().equals(isbn));
        this.size = bookInventory.size();
    }
}
