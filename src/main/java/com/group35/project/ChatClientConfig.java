package com.group35.project;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Value("${spring.ai.openai.api-key}")
    private String openAiApiKey;

    @Value("${spring.ai.openai.chat.options.mode}")
    private String openAiModelName;

    @Bean
    public ChatClient chatClient() {
        // Create an instance of OpenAiApi using its constructor
        OpenAiApi openAiApi = new OpenAiApi("https://api.openai.com", openAiApiKey);

        // Configure OpenAiChatOptions
        OpenAiChatOptions chatOptions = OpenAiChatOptions.builder()
                .withModel(openAiModelName)
                .build();

        // Create OpenAiChatModel using the OpenAiApi instance and chatOptions
        ChatModel chatModel = new OpenAiChatModel(openAiApi, chatOptions);

        // Create and return ChatClient
        return ChatClient.create(chatModel);
    }
}
