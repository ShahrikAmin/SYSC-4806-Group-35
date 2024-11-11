package com.group35.project;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import com.group35.project.Book.Book;
import com.group35.project.Inventory.Inventory;

public class StoreOwnerTests {
    private StoreOwner s1;

    @Before
    public void init(){
        Book b1 = new Book("111", "Title", "author", "publisher", "description", 1.00, "Url");
        Inventory i = new Inventory();
        s1 = new StoreOwner(i);
    }

    @Test
    public void addBook() {
        s1.addBook("111", "Title", "author", "publisher", "description", 1.00, "Url");
        assertEquals("111", s1.getInventory().getBook(0).getIsbn());
        assertEquals("Title", s1.getInventory().getBook(0).getTitle());
        assertEquals(1, s1.getInventory().getSize());
    }

    @Test
    public void editBook() {
        s1.addBook("111", "Title", "author", "publisher", "description", 1.00, "Url");
        s1.editBook(0, "111", "Title2", "author2", "publisher2", "description2", 1.00, "Url2");
        assertEquals("111", s1.getInventory().getBook(0).getIsbn());
        assertEquals("Title2", s1.getInventory().getBook(0).getTitle());
        assertEquals(1, s1.getInventory().getSize());
    }

    @Test
    public void removeBook() {
        s1.removeBook("111");
        assertEquals(0, s1.getInventory().getSize());
    }
}