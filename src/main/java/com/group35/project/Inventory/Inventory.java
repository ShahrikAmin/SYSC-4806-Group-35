package com.group35.project.Inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.group35.project.Book.Book;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor

@Entity

public class Inventory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int size;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Book> bookInventory = new ArrayList<Book>();

    public Inventory() {
        this.size = 0;
    }

    public void addBook(Book book) {
        if (bookInventory ==null) bookInventory = new ArrayList<>();
        bookInventory.add(book);
        this.size++;
    }

    public void removeBook(Book book) {bookInventory.remove(book);
    this.size--;}

    public boolean removeBook(String isbn) {
        for (Book book : bookInventory){
            if(book.getIsbn() == isbn) {
                bookInventory.remove(book);
                return true;
            }
        }
        return false;
    }

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

    public boolean hasBook(String isbn){
        for (Book b : bookInventory){
            if (b.getIsbn().equals(isbn)) {
                return true;
            }
        }
        return false;
    }


    public com.group35.project.Book.Book getBook(int index) {
        return bookInventory.get(index);
    }

    public Long getId() {
        return id;
    }

    public int getSize() {
        return bookInventory.size();
    }

    public List<Book> getBooks() {
        return bookInventory;
    }

    public void setSize(int size){
        this.size = size;
    }

    public String toString() {
        String output = "";
        for (Book b : bookInventory) {
            output += "Isbn:" + b.getIsbn() + " Title: " +  b.getTitle() + " Author: " + b.getAuthor() + "\n";
        }

        return output;
    }
}
