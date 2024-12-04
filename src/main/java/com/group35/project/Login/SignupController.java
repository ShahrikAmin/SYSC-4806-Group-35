package com.group35.project.Login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignupController {

    private final InMemoryUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignupController(InMemoryUserDetailsManager userDetailsManager, PasswordEncoder passwordEncoder) {
        this.userDetailsManager = userDetailsManager;
        this.passwordEncoder = passwordEncoder;
    }

    // Handle GET requests for the signup page
    @GetMapping("/signup")
    public String showSignupPage() {
        return "signup"; // Make sure there's a Thymeleaf template named signup.html
    }

    // Handle form submissions for signup
    @PostMapping("/signup")
    public String signup(String username, String password, String confirmPassword, String email, Model model) {
        if (!password.equals(confirmPassword)) {
            model.addAttribute("signupError", "Passwords do not match.");
            return "signup";
        }

        try {
            userDetailsManager.createUser(
                    org.springframework.security.core.userdetails.User.withUsername(username)
                            .password(passwordEncoder.encode(password))
                            .roles("USER")
                            .build()
            );
        } catch (Exception e) {
            model.addAttribute("signupError", "Signup failed: " + e.getMessage());
            return "signup";
        }

        return "redirect:/login?signupSuccess=true";
    }
}
