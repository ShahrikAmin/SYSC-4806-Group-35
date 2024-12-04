package com.group35.project.ShoppingCart;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
public class ShoppingCartLogoutHandler implements LogoutHandler {

    private final ShoppingCart shoppingCart;

    public ShoppingCartLogoutHandler(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        System.out.println("Logging out user and clearing shopping cart.");
        shoppingCart.clearCart(); // Clear the shopping cart
    }
}
