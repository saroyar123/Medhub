package com.AI.Demo.Service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Service;

@Service
public class AiReviewService {

//    private final ChatClient chatClient;

//    public AiReviewService(ChatClient chatClient){
//        this.chatClient=chatClient;
//    }
//
//    public String chat(String conversionId,String userMessage){
//        return chatClient
//                .prompt()
//                .advisors(adviser->
//                        adviser.param(
//                                ChatMemory.CONVERSATION_ID,
//                                conversionId))
//                .user(userMessage)
//                .call()
//                .content();
//    }
}
