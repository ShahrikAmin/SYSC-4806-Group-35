package com.group35.project.ShoppingCart;

import com.group35.project.Inventory.Inventory;
import com.group35.project.Book.Book;
import com.group35.project.User.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CartItem> items;


    @Setter
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public ShoppingCart(long id) {
        this.id = id;
        this.items = new ArrayList<>();
    }

    public void addItem(Book book, int count) {
        for (CartItem item : items) {
            if (item.getBook().equals(book)) {
                item.setCount(item.getCount() + count);
                return;
            }
        }
        items.add(new CartItem(book, count));
    }

    public void removeItem(Book book) {
        items.removeIf(item -> item.getBook().equals(book));
    }

    public void updateItemCount(Book book, int count) {
        for (CartItem item : items) {
            if (item.getBook().equals(book)) {
                item.setCount(count);
                return;
            }
        }
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getBook().getPrice() * item.getCount();
        }
        return total;
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Shopping Cart{" +
                "id=" + id +
                ", cartItems=" + items.toString() +
                '}';
    }

    // Nested CartItem class
    @Entity
    public static class CartItem {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Getter
        @Setter
        @ManyToOne
        private Book book;

        @Getter
        @Setter
        private int count;

        public CartItem(Book book, int count) {
            this.book = book;
            this.count = count;
        }

        public CartItem() {

        }

        @Override
        public String toString() {
            return "CartItem{" +
                    "book=" + book +
                    ", count=" + count +
                    '}';
        }
    }
}