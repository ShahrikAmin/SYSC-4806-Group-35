package com.group35.project;

import com.group35.project.Book.Book;
import com.group35.project.Inventory.Inventory;

public class StoreOwner {
    private Inventory inventory;

    public StoreOwner() {
        this.inventory = new Inventory();
    }

    public StoreOwner(Inventory inventory) {
        this.inventory = inventory;
    }

    public void addBook(String isbn, String title, String author, String publisher, String description, Double price, String pictureUrl) {
        Book book = new Book(isbn, title, author, publisher, description, price, pictureUrl);
        inventory.addBook(book);
        System.out.println("Book added to inventory: " + book);
    }

    public boolean removeBook(String isbn) {
        boolean removed = inventory.removeBook(isbn);
        if (removed) {
            System.out.println("Book removed from inventory with ISBN: " + isbn);
        } else {
            System.out.println("Book not found with ISBN: " + isbn);
        }
        return removed;
    }

//    public void displayInventory() {
//        inventory.printBooks();
//    }

    public boolean editBook(int index, String newIsbn, String newTitle, String newAuthor, String newPublisher, String newDescription, Double newPrice, String newPictureUrl) {
        Book book = inventory.getBook(index);
        if (book != null) {
            book.setIsbn(newIsbn);
            book.setTitle(newTitle);
            book.setAuthor(newAuthor);
            book.setPublisher(newPublisher);
            book.setDescription(newDescription);
            book.setPrice(newPrice);
            book.setPictureUrl(newPictureUrl);
            System.out.println("Book at index " + index + " has been updated: " + book);
            return true;
        } else {
            System.out.println("No book found at index: " + index);
            return false;
        }
    }
}
