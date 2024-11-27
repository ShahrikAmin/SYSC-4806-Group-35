package com.group35.project;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class GoogleBooksService {

    @Value("${google.books.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public GoogleBooksService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Method to fetch books from Google Books API
    public String searchBooks(String query) {
        // Build the API request URL
        String url = UriComponentsBuilder.fromHttpUrl("https://www.googleapis.com/books/v1/volumes")
                .queryParam("q", query)
                .queryParam("key", apiKey)
                .build()
                .toUriString();

        // Make the GET request
        return restTemplate.getForObject(url, String.class);
    }

    // Method to parse the JSON response
    public void parseBookDetails(String jsonResponse) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(jsonResponse);

        // Check if there are any results
        if (root.has("items") && root.path("items").isArray()) {
            JsonNode firstItem = root.path("items").get(0).path("volumeInfo");

            // Extract book details
            String title = firstItem.path("title").asText();
            String authors = firstItem.path("authors").get(0).asText();
            double averageRating = firstItem.has("averageRating") ? firstItem.path("averageRating").asDouble() : 0.0;

            // Print or return parsed data
            System.out.println("Title: " + title);
            System.out.println("Author: " + authors);
            System.out.println("Rating: " + averageRating);
        } else {
            System.out.println("No books found for the given query.");
        }
    }
}
