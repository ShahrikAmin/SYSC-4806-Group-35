package com.group35.project.Inventory.InventoryTests;

import com.group35.project.Inventory.Inventory;
import static org.junit.Assert.*;
import java.awt.print.Book;

public class InventoryTest {
    private Inventory inventory;
    private Book book1,book2;


    public void setUp() {
        this.inventory = new Inventory();

        Book book1 = new Book();

        book1.setISBN();
        book1.setAuthor();
        book1.setPublisher();

        Book book2 = new Book();
        book2.setISBN();
        book2.setAuthor();
        book2.setPublisher();

    }

    @org.junit.Test
    public void addBookTest(){
        this.inventory.addBook(book1);
        this.inventory.addBook(book2);

        assertEquals(2, inventory.getSize());
    }

    @org.junit.Test
    public void removeBookTest(){
        this.inventory.addBook(book1);
        this.inventory.addBook(book2);

        inventory.removeBook(book1);
        assertEquals(1, inventory.getSize());
    }
}
