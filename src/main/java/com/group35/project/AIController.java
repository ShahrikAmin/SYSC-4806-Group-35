package com.group35.project;

import com.group35.project.Inventory.Inventory;
import com.group35.project.Inventory.InventoryRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;

@Controller
public class AIController {

    @Autowired
    ChatClient chatClient;

    @Autowired
    InventoryRepository inventoryRepository; // Inject the repository

    @GetMapping("/AskAI")
    public String showPromptPage() {
        return "ask-ai"; // Returns the HTML page "ask-ai.html"
    }

    @PostMapping("/AskAI")
    public String handlePrompt(@RequestParam String msg, Model model) {
        try {
            Inventory inventory = inventoryRepository.findById(1L);

            String inventoryData = inventory.toString();
            String prompt = "Here is our book inventory:\n" + inventoryData +
                    "\nBased on this inventory, " + msg;

            String response = chatClient.prompt(prompt).call().content();
            model.addAttribute("response", response); // Add response to the model
        } catch (Exception e) {
            model.addAttribute("response", "An error occurred while processing your request.");
        }
        model.addAttribute("msg", msg); // Add user message to the model
        return "ask-ai"; // Returns the same HTML page with updated data
    }
}
