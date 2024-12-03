package com.group35.project.ShoppingCart;

import com.group35.project.Book.Book;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public ShoppingCart createShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();
        return shoppingCartRepository.save(shoppingCart);
    }

    public Optional<ShoppingCart> getShoppingCart(Long id) {
        return shoppingCartRepository.findById(id);
    }

    public List<ShoppingCart> getAllShoppingCarts() {
        return shoppingCartRepository.findAll();
    }

    public ShoppingCart getCart(HttpSession session) {
        // Retrieve the cart ID from the session
        Long userId = (Long) session.getAttribute("user_id");

        return shoppingCartRepository.findByUserId(userId).orElse(new ShoppingCart(1));
    }

    public ShoppingCart addItemToCart(Long cartId, Book book, int count) {
        Optional<ShoppingCart> optionalCart = shoppingCartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            ShoppingCart shoppingCart = optionalCart.get();
            shoppingCart.addItem(book, count);
            return shoppingCartRepository.save(shoppingCart);
        }
        return null;
    }

    public ShoppingCart removeItemFromCart(Long cartId, Book book) {
        Optional<ShoppingCart> optionalCart = shoppingCartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            ShoppingCart shoppingCart = optionalCart.get();
            shoppingCart.removeItem(book);
            return shoppingCartRepository.save(shoppingCart);
        }
        return null;
    }

    public ShoppingCart updateItemCount(Long cartId, Book book, int count) {
        Optional<ShoppingCart> optionalCart = shoppingCartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            ShoppingCart shoppingCart = optionalCart.get();
            shoppingCart.updateItemCount(book, count);
            return shoppingCartRepository.save(shoppingCart);
        }
        return null;
    }

    public void deleteShoppingCart(Long id) {
        shoppingCartRepository.deleteById(id);
    }

    public double getTotalPrice(Long cartId) {
        Optional<ShoppingCart> optionalCart = shoppingCartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            ShoppingCart shoppingCart = optionalCart.get();
            return shoppingCart.getTotalPrice();
        }
        return 0;
    }

    public ShoppingCart findShoppingCartByUserId(Long userId) {
        return shoppingCartRepository.findByUserId(userId).orElse(null);
    }
}