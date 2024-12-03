package com.group35.project;
import com.group35.project.Book.Book;
import com.group35.project.Book.BookService;
import com.group35.project.Inventory.Inventory;
import com.group35.project.Inventory.InventoryRepository;
import com.group35.project.Inventory.InventoryService;
import com.group35.project.ShoppingCart.ShoppingCart;
import com.group35.project.ShoppingCart.ShoppingCartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ThymeleafController {

    private final InventoryService inventoryService;
    private final BookService bookService;
    private final InventoryRepository inventoryRepository;
    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ThymeleafController(InventoryService inventoryService, BookService bookService, InventoryRepository inventoryRepository, ShoppingCartService shoppingCartService) {
        this.inventoryService = inventoryService;
        this.bookService = bookService;
        this.inventoryRepository = inventoryRepository;
        this.shoppingCartService = shoppingCartService;
    }

    // Display inventory page with the list of books in inventory (accessible to both roles)
    @GetMapping("/user/home")
    public String showInventory(@RequestParam(value = "isbn", required = false) String isbn,
                                @RequestParam(value = "title", required = false) String title,
                                @RequestParam(value = "keyword", required = false) String keyword,
                                @RequestParam(value = "author", required = false) String author,
                                @RequestParam(value = "publisher", required = false) String publisher,
                                Model model) {

        List<Book> books = List.of();
        String searchType = null;

        // Check for search by ISBN
        if (isbn != null && !isbn.isEmpty()) {
            try {
                Book book = bookService.findBookByIsbn(isbn);
                books = List.of(book);
                searchType = "ISBN: " + isbn;
            } catch (Exception e) {
                searchType = "No results for ISBN: " + isbn;
            }
        }
        // Check for search by Title
        else if (title != null && !title.isEmpty()) {
            books = bookService.findBooksByTitle(title);
            searchType = !books.isEmpty() ? "Title: " + title : "No results for Title: " + title;
        }
        // Check for search by Keyword in Title
        else if (keyword != null && !keyword.isEmpty()) {
            books = bookService.findBooksByTitleContaining(keyword);
            searchType = !books.isEmpty() ? "Keyword in Title: " + keyword : "No results for Keyword: " + keyword;
        }
        // Check for search by Author
        else if (author != null && !author.isEmpty()) {
            books = bookService.findBooksByAuthor(author);
            searchType = !books.isEmpty() ? "Author: " + author : "No results for Author: " + author;
        }
        // Check for search by Publisher
        else if (publisher != null && !publisher.isEmpty()) {
            books = bookService.findBooksByPublisher(publisher);
            searchType = !books.isEmpty() ? "Publisher: " + publisher : "No results for Publisher: " + publisher;
        }
        //Return Inventory
        else {
            Inventory inventory = inventoryRepository.findById(1L);
            if (inventory != null) {
                books = new ArrayList<>(inventory.getAllBooks().values());
                searchType = "All Books";
            } else {
                searchType = "No inventory found!";
            }
        }

        model.addAttribute("books", books);
        model.addAttribute("searchType", searchType);

        List<Inventory> inventoryList = new ArrayList<>();
        inventoryRepository.findAll().forEach(inventoryList::add);
        model.addAttribute("inventoryList", inventoryList);

        return "inventory-view";
    }

    // Display the shopping cart
    @GetMapping("/user/shopping-cart")
    public String showCart(Model model, HttpSession session) {

        ShoppingCart cart = shoppingCartService.getCart(session);
        model.addAttribute("shoppingCart", cart);
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
        if(inventoryService.getAllInventory().get(0).hasBook(bookId)){
            inventoryService.editBook(book,1L);
        }else {
            bookService.createBook(book);  // Save book in BookService
            inventoryService.addBook(book, 1L);  // Add to inventory (assuming inventoryId = 1)
        }
        model.addAttribute("message", "Book added successfully");
        return "redirect:/user/home";
    }

//     Handle removing a book from the inventory (accessible only to STORE_OWNER)
    @PostMapping("/storeowner/removeBook")
    public String removeBookFromInventory(@RequestParam("isbn") String isbn, Model model) {
        inventoryService.removeBookByISBN(isbn, 1L);  // Remove from inventory (assuming inventoryId = 1)
        model.addAttribute("message", "Book removed successfully");
        return "redirect:/storeowner/home";
    }
}