package com.group35.project.ShoppingCart;

import com.group35.project.Book.Book;
import com.group35.project.Inventory.Inventory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shoppingcart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping
    public ResponseEntity<ShoppingCart> createShoppingCart() {
        ShoppingCart shoppingCart = shoppingCartService.createShoppingCart();
        return ResponseEntity.ok(shoppingCart);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShoppingCart> getShoppingCart(@PathVariable Long id) {
        Optional<ShoppingCart> shoppingCart = shoppingCartService.getShoppingCart(id);
        return shoppingCart.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ShoppingCart>> getAllShoppingCarts() {
        List<ShoppingCart> shoppingCarts = shoppingCartService.getAllShoppingCarts();
        return ResponseEntity.ok(shoppingCarts);
    }
    @GetMapping("/view")
    public String viewCart(Model model) {
        List<ShoppingCart> shoppingCarts = shoppingCartService.getAllShoppingCarts();
        model.addAttribute("shoppingCarts", shoppingCarts);
        return "shopping-cart";
    }

    @PostMapping("/{cartId}/add")
    public ResponseEntity<ShoppingCart> addItemToCart(@PathVariable Long cartId, @RequestBody Book book, @RequestParam int count) {
        ShoppingCart shoppingCart = shoppingCartService.addItemToCart(cartId, book, count);
        if (shoppingCart != null) {
            return ResponseEntity.ok(shoppingCart);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{cartId}/remove")
    public ResponseEntity<ShoppingCart> removeItemFromCart(@PathVariable Long cartId, @RequestBody Book book) {
        ShoppingCart shoppingCart = shoppingCartService.removeItemFromCart(cartId, book);
        if (shoppingCart != null) {
            return ResponseEntity.ok(shoppingCart);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{cartId}/update")
    public ResponseEntity<ShoppingCart> updateItemCount(@PathVariable Long cartId, @RequestBody Book book, @RequestParam int count) {
        ShoppingCart shoppingCart = shoppingCartService.updateItemCount(cartId, book, count);
        if (shoppingCart != null) {
            return ResponseEntity.ok(shoppingCart);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingCart(@PathVariable Long id) {
        shoppingCartService.deleteShoppingCart(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{cartId}/total")
    public ResponseEntity<Double> getTotalPrice(@PathVariable Long cartId) {
        double totalPrice = shoppingCartService.getTotalPrice(cartId);
        return ResponseEntity.ok(totalPrice);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ShoppingCart> findShoppingCartByUserId(@PathVariable Long userId) {
        ShoppingCart shoppingCart = shoppingCartService.findShoppingCartByUserId(userId);
        if (shoppingCart != null) {
            return ResponseEntity.ok(shoppingCart);
        }
        return ResponseEntity.notFound().build();
    }
}
