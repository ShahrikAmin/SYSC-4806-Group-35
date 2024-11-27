package com.group35.project;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.group35.project.Inventory.Inventory;
import com.group35.project.Inventory.InventoryRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

@Controller
public class AIController {

    @Autowired
    GoogleBooksService googleBooksService; // Inject the service directly

    @Autowired
    ChatClient chatClient;

    @Autowired
    InventoryRepository inventoryRepository; // Inject the repository

    @Autowired
    RestTemplate restTemplate; // Inject RestTemplate to call /search

    @GetMapping("/AskAI")
    public String showPromptPage() {
        return "ask-ai"; // Returns the HTML page "ask-ai.html"
    }

    @PostMapping("/AskAI")
    public String handlePrompt(@RequestParam String msg, Model model) {
        try {
            // Extract book title from user's message
            String bookTitle = extractBookTitleFromUserMessage(msg);

            // Fetch book details from Google Books API
            String bookDetailsResponse = googleBooksService.searchBooks(bookTitle);
            System.out.println(bookDetailsResponse);

            // Parse the response to extract the rating
            String rating = extractRatingFromResponse(bookDetailsResponse);

            // Fetch inventory data from the repository
            Inventory inventory = inventoryRepository.findById(1L);

            // Convert inventory data to a string
            String inventoryData = inventory.toString();


            // Append book details to the GPT prompt
            String prompt = "Here is our book inventory:\n" + inventoryData +
                    "\nAdditionally, here are the details for the book '" + bookTitle + ", with rating of " + rating + "':\n" + bookDetailsResponse +
                    "\nBased on this inventory, only using these books in our inventory and not any book from the internet, " + msg;

            System.out.println(prompt);

            // Query OpenAI's GPT-3.5
            String response = chatClient.prompt(prompt).call().content();

            // Add GPT-3.5 response to the model
            model.addAttribute("response", response);
        } catch (Exception e) {
            model.addAttribute("response", "An error occurred while processing your request.");
        }

        // Add user message to the model
        model.addAttribute("msg", msg);
        return "ask-ai"; // Returns the same HTML page with updated data
    }


    private String extractBookTitleFromUserMessage(String msg) {
        int start = msg.indexOf('"') + 1;
        int end = msg.indexOf('"', start);
        if (start > 0 && end > start) {
            return msg.substring(start, end).trim();
        }

        String[] words = msg.split(" ");
        int ratingIndex = -1;

        for (int i = 0; i < words.length; i++) {
            if (words[i].equalsIgnoreCase("rating")) {
                ratingIndex = i;
                break;
            }
        }

        if (ratingIndex != -1 && ratingIndex + 1 < words.length) {
            StringBuilder potentialTitle = new StringBuilder();
            for (int i = ratingIndex + 1; i < words.length; i++) {
                potentialTitle.append(words[i]).append(" ");
            }
            return potentialTitle.toString().trim();
        }

        return msg.trim();
    }

    private String extractRatingFromResponse(String jsonResponse) throws Exception {
        // Parse JSON response to extract the rating
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(jsonResponse);

        if (root.has("items") && root.path("items").isArray()) {
            JsonNode firstItem = root.path("items").get(0).path("volumeInfo");
            if (firstItem.has("averageRating")) {
                return firstItem.path("averageRating").asText();
            }
        }
        return "N/A"; // Return "N/A" if no rating is found
    }

}
