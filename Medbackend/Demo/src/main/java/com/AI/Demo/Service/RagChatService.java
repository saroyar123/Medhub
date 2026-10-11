package com.AI.Demo.Service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import javax.crypto.SealedObject;

@Service
public class RagChatService {

    private final ChatClient chatClient;

    public RagChatService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore){
        System.out.println("Rag chat system is ready");

        SearchRequest searchRequest=SearchRequest.builder()
                .topK(1)
                .similarityThreshold(0.4)
                .build();

        this.chatClient=chatClientBuilder
                .defaultSystem("You are a helpful AI assistant specialized in answering questions based on the provided LLD document context.")
                .defaultAdvisors(
                        new SimpleLoggerAdvisor(),
                        QuestionAnswerAdvisor.builder(vectorStore)
                        .searchRequest(searchRequest).build())
                .build();
    }

    public String askQuestion(String message){
        System.out.println("chat model call for ans");
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
