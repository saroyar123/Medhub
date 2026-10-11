package com.medhub.DoctorSuggestion.Controller;

import com.medhub.DoctorSuggestion.service.MedHubChatService;
import com.medhub.DoctorSuggestion.service.MedHubKnowledgeLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/v1/rag")
public class RagController {
    private final MedHubKnowledgeLoader medHubKnowledgeLoader;
    private final MedHubChatService medHubChatService;

    public RagController(MedHubKnowledgeLoader medHubKnowledgeLoader, MedHubChatService medHubChatService) {
        this.medHubKnowledgeLoader = medHubKnowledgeLoader;
        this.medHubChatService = medHubChatService;
    }

    @PostMapping
    public String createEmbedding(){
        try {
            medHubKnowledgeLoader.loadSymptomKnowledgeBase();
            return "Success";
        }catch (Exception e){
            System.out.println(e.getMessage());

        }
        return "Failed";
    }

    @PostMapping("/chat")
    public ResponseEntity<Map<String, String>> chatWithMedHub(@RequestBody Map<String, String> requestPayload) {
        String userMessage = requestPayload.get("message");

        if (userMessage == null || userMessage.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Message cannot be empty"));
        }

        // Call the RAG chat service
        String aiResponse = medHubChatService.getSpecialistRecommendation(userMessage);

        return ResponseEntity.ok(Map.of("response", aiResponse));
    }
}
