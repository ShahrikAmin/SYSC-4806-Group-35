package com.group35.project.Inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryService {
    private final InventoryRepository repository;

    @Autowired
    public InventoryService(InventoryRepository repository) {this.repository = repository;}

    public List<Inventory> getAllInventory() {
        List<Inventory> inventory = new ArrayList<>();
        for (Inventory inv: repository.findAll()){
            Inventory i = new Inventory(inv.getId());
            if(inv.getSize() != 0){
                for (Book b : inv.getBooks()){
                    i.addBook(b);
                }
            }
            inventory.add(i);
        }
        return inventory;
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

    public void addBook(Book book, Long inventoryId) {
        Inventory inventory = repository.findById(inventoryId)
                .orElseThrow(() -> new IllegalStateException("Inventory with id " + inventoryId + " does not exist"));

        inventory.addBook(book);
        repository.save(inventory);
    }

    public void removeBook(Long inventoryId, Long BookId) {
        Inventory inventory = repository.findById(inventoryId)
                .orElseThrow(() -> new IllegalStateException("Inventory with id "
                        + inventoryId + " does not exist."));

        inventory.removeBookWithId(BookId);

        repository.save(inventory);
    }

    public void removeBookWithISBN(String isbn, Long inventoryId) {
        Inventory inventory = repository.findById(inventoryId)
                .orElseThrow(() -> new IllegalStateException("Inventory with id "
                        + inventoryId + " does not exist."));

        inventory.removeBookWithISBN(isbn);
        repository.save(inventory);
    }
}


