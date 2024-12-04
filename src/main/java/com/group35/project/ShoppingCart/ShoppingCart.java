package com.group35.project.ShoppingCart;

import com.group35.project.Book.Book;
import com.group35.project.Inventory.Inventory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Scope("session")
public class ShoppingCart {

    private final Inventory inventory;
    private final Map<Book, Integer> cartItems;

    public ShoppingCart(Inventory inventory) {
        this.inventory = inventory;
        this.cartItems = new HashMap<>();
    }

    public void addToCart(Book book, int quantity) {
        cartItems.put(book, cartItems.getOrDefault(book, 0) + quantity);
    }

    public boolean removeFromCart(Book book) {
        return cartItems.remove(book) != null;
    }

    public Map<Book, Integer> getCartItems() {
        return cartItems;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void clearCart() {
        cartItems.clear();
    }
}
