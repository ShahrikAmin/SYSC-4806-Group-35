package com.group35.project;

import com.group35.project.Book.Book;
import com.group35.project.Book.BookService;
import com.group35.project.Inventory.Inventory;
import com.group35.project.Inventory.InventoryRepository;
import com.group35.project.Inventory.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {

    private final InventoryService inventoryService;
    private final BookService bookService;
    private final InventoryRepository inventoryRepository;

    @Autowired
    public ThymeleafController(InventoryService inventoryService, BookService bookService, InventoryRepository inventoryRepository) {
        this.inventoryService = inventoryService;
        this.bookService = bookService;
        this.inventoryRepository = inventoryRepository;
    }

    // Display inventory page with the list of books in inventory
    @GetMapping("/books")
    public String showInventory(Model model) {
        //List<Inventory> inventoryList = inventoryService.getAllInventory();
        Inventory inventory = inventoryRepository.findById(1L);
        model.addAttribute("inventory", inventory);
        return "inventory-view";
    }

    // Display seller page for adding and removing books
    @GetMapping("/seller")
    public String showSellerPage(Model model) {
        model.addAttribute("message", "Welcome to the Seller Page!");
        return "seller-view";
    }

    // Handle adding a book to the inventory
    @PostMapping("/seller/addBook")
    public String addBookToInventory(@RequestParam("isbn") String bookId,
                                     @RequestParam("title") String title,
                                     @RequestParam("author") String author,
                                     @RequestParam("publisher") String publisher,
                                     @RequestParam("description") String description,
                                     @RequestParam("price") Double price,
                                     @RequestParam("pictureUrl") String pictureUrl,
                                     Model model) {
        Book book = new Book(bookId, title, author, publisher, description, price, pictureUrl);
        bookService.createBook(book);  // Save book in BookService
        inventoryService.addBook(book, 1L);  // Add to inventory (assuming inventoryId = 1)
        model.addAttribute("message", "Book added successfully");
        return "redirect:/books";
    }

    // Handle removing a book from the inventory
    @PostMapping("/seller/removeBook")
    public String removeBookFromInventory(@RequestParam("isbn") Long bookId, Model model) {
        inventoryService.removeBook(1L, bookId);  // Remove from inventory (assuming inventoryId = 1)
        model.addAttribute("message", "Book removed successfully");
        return "redirect:/books";
    }
}
