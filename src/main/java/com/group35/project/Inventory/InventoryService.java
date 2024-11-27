package com.group35.project.Inventory;

import com.group35.project.Book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group35.project.Book.Book;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository repository;
    private final BookRepository bookRepository;

    @Autowired
    public InventoryService(InventoryRepository repository, BookRepository bookRepository) {
        this.repository = repository;
        this.bookRepository = bookRepository;
    }
    public List<Inventory> getAllInventory() {
        List<Inventory> inventories = new ArrayList<>();
        repository.findAll().forEach(inventories::add);
        return inventories;
    }
    public void createNewInventory() {
        Inventory newInventory = new Inventory();
        newInventory.setSize(0);
        repository.save(newInventory);
    }

    public void deleteInventory(Long id) {
        boolean exists = repository.existsById(id);
        if(!exists) throw new IllegalStateException("Inventory with id" + id + " does not exist");
        repository.deleteById(id);
    }

    public boolean addBook(Book book, Long inventoryId) {
        Inventory inventory = repository.findById(inventoryId)
                .orElseThrow(() -> new IllegalStateException("Inventory with id " + inventoryId + " does not exist"));
        book = bookRepository.save(book);
        inventory.addBook(book);
        repository.save(inventory);
        return true;
    }


    public boolean editBook(Book book, Long inventoryId) {
        Inventory inventory = repository.findById(inventoryId)
                .orElseThrow(() -> new IllegalStateException("Inventory with id " + inventoryId + " does not exist"));

        for(Book b : inventory.getAllBooks().values()){
            if (b.getIsbn().equals(book.getIsbn())){
                b.setTitle(book.getTitle());
                b.setAuthor(book.getAuthor());
                b.setPublisher(book.getPublisher());
                b.setDescription(book.getDescription());
                b.setPrice(book.getPrice());
                b.setPictureUrl(book.getPictureUrl());
                inventory.increaseStock(b.getId(),1);
                bookRepository.save(b);
                return true;
            }
        }

        return false;
    }

    public void removeBook(Long inventoryId, Long BookId) {
        Inventory inventory = repository.findById(inventoryId)
                .orElseThrow(() -> new IllegalStateException("Inventory with id "
                        + inventoryId + " does not exist."));

        inventory.removeBook(BookId);
        repository.save(inventory);
    }

    public void removeBookByISBN(String isbn, Long inventoryId) {
        Inventory inventory = repository.findById(inventoryId)
                .orElseThrow(() -> new IllegalStateException("Inventory with id "
                        + inventoryId + " does not exist."));

        inventory.removeBookByISBN(isbn);
        repository.save(inventory);
    }
}


