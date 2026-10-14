package com.medhub.DoctorSuggestion.service;

import com.medhub.DoctorSuggestion.Dto.LLMRespponse;
import com.medhub.DoctorSuggestion.ToolsCall.GetDoctorBySpeciality;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MedHubChatService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;
    private final GetDoctorBySpeciality getDoctorBySpeciality;
    private final ChatMemory chatMemory =MessageWindowChatMemory.builder().build();

    public MedHubChatService(ChatClient.Builder chatClientBuilder,
                             VectorStore vectorStore,
                             GetDoctorBySpeciality getDoctorBySpeciality) {
        this.vectorStore = vectorStore;
        this.getDoctorBySpeciality = getDoctorBySpeciality;
        this.chatClient = chatClientBuilder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    public LLMRespponse getSpecialistRecommendation(String userId, String userSymptomMessage) {
        // 1. Search symptom/clinical guidelines context from vector store
        List<Document> retrievedGuidelines = vectorStore.similaritySearch(
                SearchRequest.builder().query(userSymptomMessage).topK(2).build()
        );

        String clinicalContext = retrievedGuidelines.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        String systemPrompt = String.format("""
            You are MedHub, an expert clinical routing assistant. 
            Analyze the user's latest message and full conversation history against the clinical guidelines context below.
            
            Clinical Guidelines Context:
            %s
            
            Rules:
            - If information is not sufficient to identify a definitive diagnosis, DO NOT guess yet. Ask about related symptoms to cross-question (strictly within 2-3 questions max). 
            - Populate the `messageToUser` field as an array/list of individual strings (e.g., each question or statement as a separate element in the list). Leave `recommendedSpecialty` null and `doctors` empty while asking questions.
            - If there is finally enough information across the conversation, determine the right medical specialty and urgency level, put your concluding summary strings into `messageToUser`, and invoke the tool `fetchDoctorsBySpecialty` passing the exact specialty name to retrieve matching doctors.
            """, clinicalContext);

        // 3. Call ChatClient with structured entity mapping
        return chatClient.prompt()
                .system(systemPrompt)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, userId))
                .user(userSymptomMessage)
                .tools(getDoctorBySpeciality)
                .call()
                .entity(LLMRespponse.class);
    }

    private String extractSpecialty(String response) {
        if (response.contains("Cardiologist")) return "Cardiologist";
        if (response.contains("Dermatologist")) return "Dermatologist";
        if (response.contains("Pediatrician")) return "Pediatrician";
        if (response.contains("Neurologist")) return "Neurologist";
        return "General Practitioner";
    }
}