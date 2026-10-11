package com.medhub.DoctorSuggestion.service;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MedHubChatService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public MedHubChatService(ChatClient.Builder chatClientBuilder, VectorStore vectorStore) {
        this.vectorStore = vectorStore;
        // Build ChatClient utilizing your OpenRouter configuration from application.yml
        this.chatClient = chatClientBuilder.build();
    }

    public String getSpecialistRecommendation(String userSymptomMessage) {
        // 1. Perform similarity search against your vectorized symptom knowledge base
        // Fetching top 2 most relevant symptom guidelines
        List<Document> retrievedGuidelines = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(userSymptomMessage)
                        .topK(2).build()
        );

        // 2. Combine the retrieved chunks into a single context block
        String clinicalContext = retrievedGuidelines.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        // 3. Construct the prompt and call the LLM using Spring AI's fluent ChatClient API
        return chatClient.prompt()
                .system("""
                You are MedHub, an expert and empathetic AI clinical routing assistant. 
                Your job is to analyze the patient's described symptoms, match them with the provided clinical guidelines context, and recommend the correct medical specialist.
                
                Always structure your response clearly:
                1. **Recommended Specialist:** (e.g., Cardiologist, Dermatologist, etc.)
                2. **Urgency Level:** (e.g., High, Moderate, Low)
                3. **Clinical Reasoning:** (Brief explanation based on context)
                4. **Next Steps:** (Encourage booking an appointment via MedHub)
                
                If the symptoms suggest a life-threatening emergency (like severe chest pain), explicitly advise them to seek emergency services immediately.
                """)
                .user(u -> u.text("""
                Patient Symptom Input: {user_message}
                
                Retrieved Clinical Guidelines Context:
                {clinical_context}
                """)
                        .param("user_message", userSymptomMessage)
                        .param("clinical_context", clinicalContext.isEmpty() ? "No direct guidelines found." : clinicalContext))
                .call()
                .content();
    }
}
