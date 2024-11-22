package com.group35.project.Login;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    // display the custom login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // using the user role, redicrect the user appropriately
    @GetMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole("ROLE_STORE_OWNER")) {
            return "redirect:/storeowner/home";
        } else if (request.isUserInRole("ROLE_USER")) {
            return "redirect:/user/home";
        } else {
            return "redirect:/login?error=true";
        }
    }
}
