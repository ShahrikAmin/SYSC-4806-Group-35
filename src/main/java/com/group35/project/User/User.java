package com.group35.project.User;


import com.group35.project.ShoppingCart.ShoppingCart;
import jakarta.persistence.*;
import com.group35.project.Book.Book;
import com.group35.project.Inventory.Inventory;
import lombok.Data;


import java.util.Map;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // this role is  either set to "ROLE_USER" or "ROLE_STORE_OWNER"

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ShoppingCart shoppingCart;

    public User() {
        this.shoppingCart = new ShoppingCart();
        this.shoppingCart.setUser(this);
    }

    public Map<Long, Book> browseBooks(Inventory inventory) {
        return inventory.getAllBooks();
    }

//    public void addToCart(Book book) {
//        shoppingCart.addBook(book);
//        System.out.println("Book added to shopping cart: " + book);
//    }

    public void checkout(Inventory inventory) {
//        if (shoppingCart.checkout(inventory)) {
//            System.out.println("Checkout successful.");
//        } else {
//            System.out.println("Checkout failed: Not enough inventory.");
//        }
    }

}
