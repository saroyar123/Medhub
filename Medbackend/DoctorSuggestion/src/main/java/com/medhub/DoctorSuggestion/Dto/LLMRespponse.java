package com.medhub.DoctorSuggestion.Dto;

import java.util.List;
import java.util.Map;

public record LLMRespponse(
        List<String> messageToUser,         // Either clarifying questions or final diagnosis summary
        String recommendedSpecialty,   // e.g. "Cardiologist" (null if still asking questions)
        String urgencyLevel,           // e.g. "High", "Moderate", "Low"
        List<Map<String, Object>> doctors
) { }
