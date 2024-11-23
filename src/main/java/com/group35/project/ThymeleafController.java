package com.group35.project;

import com.group35.project.Book.Book;
import com.group35.project.Book.BookService;
import com.group35.project.Inventory.Inventory;
import com.group35.project.Inventory.InventoryRepository;
import com.group35.project.Inventory.InventoryService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafController {
    private static final Logger logger = LoggerFactory.getLogger(ThymeleafController.class);

    private final InventoryService inventoryService;
    private final BookService bookService;
    private final InventoryRepository inventoryRepository;

    @Autowired
    public ThymeleafController(InventoryService inventoryService, BookService bookService, InventoryRepository inventoryRepository) {
        this.inventoryService = inventoryService;
        this.bookService = bookService;
        this.inventoryRepository = inventoryRepository;
    }

    // Display inventory page with search functionality
    @GetMapping("/books")
    public String showInventory(@RequestParam(value = "isbn", required = false) String isbn,
                                @RequestParam(value = "title", required = false) String title,
                                Model model) {
        logger.info("Search request received with ISBN: {} and Title: {}", isbn, title);

        List<Book> books = List.of();
        String searchType = null;

        if (isbn != null && !isbn.isEmpty()) {
            try {
                Book book = bookService.findBookByIsbn(isbn);
                books = List.of(book);
                searchType = "ISBN: " + isbn;
                logger.info("Book found for ISBN '{}': {}", isbn, book);
            } catch (Exception e) {
                searchType = "No results for ISBN: " + isbn;
                logger.warn("No book found for ISBN: {}", isbn, e);
            }
        } else if (title != null && !title.isEmpty()) {
            books = bookService.findBooksByTitle(title);
            if (!books.isEmpty()) {
                searchType = "Title: " + title;
                logger.info("Books found for Title '{}': {}", title, books);
            } else {
                searchType = "No results for Title: " + title;
                logger.warn("No books found for Title: {}", title);
            }
        } else {
            Inventory inventory = inventoryRepository.findById(1L);
            if (inventory != null) {
                books = inventory.getBooks();
                searchType = "All Books";
                logger.info("Returning all books from inventory.");
            } else {
                searchType = "No inventory found!";
                logger.warn("No inventory found.");
            }
        }

        model.addAttribute("books", books);
        model.addAttribute("searchType", searchType);

        return "inventory-view";
    }

    // Display the shopping cart
    @GetMapping("/user/shopping-cart")
    public String showCart(Model model, HttpSession session) {
        // Placeholder for shopping cart functionality
        // ShoppingCart cart = shoppingCartService.getCart(session);
        // model.addAttribute("cartItems", cart.getItems());
        return "shopping-cart";
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
        if (inventoryRepository.findById(1L).hasBook(bookId)) {
            inventoryService.removeBookWithISBN(bookId, 1L);
            model.addAttribute("message", "Book edited successfully");
        } else {
            model.addAttribute("message", "Book added successfully");
        }
        Book book = new Book(bookId, title, author, publisher, description, price, pictureUrl);
        bookService.createBook(book);  // Save book in BookService
        inventoryService.addBook(book, 1L);  // Add to inventory (assuming inventoryId = 1)
        return "redirect:/thymeleaf/books";
    }

    // Handle removing a book from the inventory
    @PostMapping("/seller/removeBook")
    public String removeBookFromInventory(@RequestParam("isbn") String isbn, Model model) {
        inventoryService.removeBookWithISBN(isbn, 1L);  // Remove from inventory (assuming inventoryId = 1)
        model.addAttribute("message", "Book removed successfully");
        return "redirect:/thymeleaf/books";
    }
}
