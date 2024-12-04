package com.group35.project.User;


import com.group35.project.ShoppingCart.ShoppingCart;
import jakarta.persistence.*;
import com.group35.project.Book.Book;
import com.group35.project.Inventory.Inventory;
import java.util.Map;

@Entity
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


    public User() {
    }

    public Map<Long, Book> browseBooks(Inventory inventory) {
        return inventory.getAllBooks();
    }

}
