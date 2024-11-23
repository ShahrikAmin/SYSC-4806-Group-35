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

@Controller
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

    // Display inventory page with the list of books in inventory (accessible to both roles)
    @GetMapping("/user/home")
    public String showInventory(Model model) {
        Inventory inventory = inventoryRepository.findById(1L);
        model.addAttribute("inventory", inventory);
        return "inventory-view";
    }

    // Display the shopping cart
    @GetMapping("/user/shopping-cart")
    public String showCart(Model model, HttpSession session) {

        /* NOTE TO TEAM MEMBER, WHOEVER MAKING THE SHOPPING CART, I ADDED and COMMENTED TWO BELOW LINES
        OF CODE FOR YOU
         */

        // ShoppingCart cart = shoppingCartService.getCart(session);
        // model.addAttribute("cartItems", cart.getItems());
        return "shopping-cart";
    }

    // Display seller page for adding and removing books (accessible only to STORE_OWNER)
    @GetMapping("/storeowner/home")
    public String showSellerPage(Model model) {
        model.addAttribute("message", "Welcome to the Store Owner Page!");
        return "seller-view";
    }

    // Handle adding a book to the inventory (accessible only to STORE_OWNER)
    @PostMapping("/storeowner/addBook")
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
        return "redirect:/storeowner/home";
    }

//     Handle removing a book from the inventory (accessible only to STORE_OWNER)
    @PostMapping("/storeowner/removeBook")
    public String removeBookFromInventory(@RequestParam("isbn") String isbn, Model model) {
        inventoryService.removeBookByISBN(isbn, 1L);  // Remove from inventory (assuming inventoryId = 1)
        model.addAttribute("message", "Book removed successfully");
        return "redirect:/storeowner/home";
    }
}