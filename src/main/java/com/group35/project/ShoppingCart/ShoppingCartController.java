package com.group35.project.ShoppingCart;

import com.group35.project.Book.Book;
import com.group35.project.Book.BookService;
import com.group35.project.Inventory.Inventory;
import com.group35.project.Inventory.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user/shopping-cart")
public class ShoppingCartController {

    private final ShoppingCart shoppingCart;
    private final BookService bookService;
    private final InventoryRepository inventoryRepository;

    @Autowired
    public ShoppingCartController(ShoppingCart shoppingCart, BookService bookService, InventoryRepository inventoryRepository) {
        this.shoppingCart = shoppingCart; // Use Spring-managed ShoppingCart
        this.bookService = bookService;
        this.inventoryRepository = inventoryRepository;
    }

    @GetMapping
    public String showCart(Model model) {
        model.addAttribute("cartItems", shoppingCart.getCartItems().entrySet());
        return "shopping-cart";
    }

    @PostMapping("/add")
    public String addToCart(@RequestParam("isbn") String isbn, @RequestParam("quantity") int quantity, Model model) {
        System.out.println("Adding book with ISBN: " + isbn + ", Quantity: " + quantity);

        Book book = bookService.findBookByIsbn(isbn);
        if (book != null) {
            shoppingCart.addToCart(book, quantity);
        } else {
            model.addAttribute("error", "Book not found!");
        }
        return "redirect:/user/shopping-cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam("isbn") String isbn, Model model) {
        System.out.println("Removing book with ISBN: " + isbn);

        Book book = bookService.findBookByIsbn(isbn);
        if (book != null) {
            shoppingCart.removeFromCart(book);
        } else {
            model.addAttribute("error", "Book not found in cart!");
        }
        return "redirect:/user/shopping-cart";
    }

    @PostMapping("/checkout")
    public String checkout(Model model) {
        System.out.println("Starting checkout process...");

        Inventory inventory = inventoryRepository.findById(1L);

        for (Map.Entry<Book, Integer> entry : shoppingCart.getCartItems().entrySet()) {
            Book book = entry.getKey();
            int quantity = entry.getValue();

            if (inventory.decreaseStock(book.getId(), quantity)) {
                System.out.println("Decreased stock for book: " + book.getTitle());
            } else {
                model.addAttribute("error", "Not enough stock for book: " + book.getTitle());
                return "shopping-cart";
            }
        }

        inventoryRepository.save(inventory);
        shoppingCart.clearCart(); // Clear the cart after checkout

        model.addAttribute("success", "Checkout successful!");
        return "redirect:/user/shopping-cart";
    }
}
