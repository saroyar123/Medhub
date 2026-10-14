package com.AI.Demo.controller;

import com.AI.Demo.Service.AiReviewService;
import com.AI.Demo.Service.RagChatService;
import com.AI.Demo.dto.ChatRequest;
import com.AI.Demo.dto.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private AiReviewService aiReviewService;

    @Autowired
    private RagChatService ragChatService;

//    @PostMapping
//    public ChatResponse getHelp(@RequestBody ChatRequest message){
//        return new ChatResponse(aiReviewService.chat(message.conversationId(),message.message()));
//    }

    @GetMapping
    public String askToRag(@RequestParam String message){
        return ragChatService.askQuestion(message);
    }


}
