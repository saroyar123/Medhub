package com.medhub.DoctorSuggestion.Controller;

import com.medhub.DoctorSuggestion.Dto.LLMRespponse;
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
    public ResponseEntity<LLMRespponse> chatWithMedHub(@RequestBody Map<String, String> requestPayload) {
        // Extract userId (or sessionId) and user message from the JSON payload
        String userId = requestPayload.getOrDefault("userId", "default_user");
        String userMessage = requestPayload.get("message");

        if (userMessage == null || userMessage.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        // Call the service workflow (RAG lookup -> Sufficiency check/Clarification -> Doctor API gateway)
        LLMRespponse aiResponse = medHubChatService.getSpecialistRecommendation(userId, userMessage);

        return ResponseEntity.ok(aiResponse);
    }
}
