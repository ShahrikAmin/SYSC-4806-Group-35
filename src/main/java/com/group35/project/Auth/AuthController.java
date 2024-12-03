package com.group35.project.Auth;

import com.group35.project.User.UserDto;
import com.group35.project.User.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class AuthController {
    private final UserService userService;

    // Constructor injection for UserService
    public AuthController(UserService userService) {
        this.userService = userService;
    }

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
    @GetMapping("/signUp")
    public String signUp() {
        return "signUp";
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        // Validate input
        if (userDto.userName() == null || userDto.userName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username is required.");
        }

        if (userDto.password() == null || userDto.password().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Password is required.");
        }

        // Check if the username already exists
        if (userService.usernameExists(userDto.userName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username already exists.");
        }

        // Save the user
        userService.saveUser(userDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registered successfully.");
    }
}

