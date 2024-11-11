package com.group35.project.Inventory;

import com.group35.project.Book.Book;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;


    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<Inventory> getAllInventory() {return inventoryService.getAllInventory();}

    @PostMapping
    public void createNewInventory(@RequestBody Inventory inventory) {
        inventoryService.createNewInventory();
    }

    @DeleteMapping(path = "{inventoryId}")
    public void deleteInventory(@PathVariable("inventoryId") Long id) {inventoryService.deleteInventory(id);}

    @DeleteMapping(path = "{inventoryId}/{BookId}")
    public void removeBook(@PathVariable("inventoryId") Long inventoryId, @PathVariable("BookId") Long BookId) {
        inventoryService.removeBook(inventoryId, BookId);
    }

    @PatchMapping(path = "/{inventoryId}/addBook")
    public void addBook(@PathVariable("inventoryId") Long inventoryId, @RequestBody Book book) {
        inventoryService.addBook(book, inventoryId);
    }



}
