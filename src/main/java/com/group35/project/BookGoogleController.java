package com.group35.project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookGoogleController {

    private final GoogleBooksService googleBooksService;

    public BookGoogleController(GoogleBooksService googleBooksService) {
        this.googleBooksService = googleBooksService;
    }

    @GetMapping("/search")
    public void searchBooks(@RequestParam String query) {
        try {
            // Call the service to get the JSON response
            String jsonResponse = googleBooksService.searchBooks(query);

            // Parse the response to extract book details
            googleBooksService.parseBookDetails(jsonResponse);
        } catch (Exception e) {
            System.out.println("Error fetching or parsing book data: " + e.getMessage());
        }
    }
}
