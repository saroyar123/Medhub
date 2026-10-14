package com.medhub.DoctorSuggestion.ToolsCall;

import com.medhub.DoctorSuggestion.service.DoctorApiServices;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class GetDoctorBySpeciality {
    private final DoctorApiServices doctorService;
    public GetDoctorBySpeciality(DoctorApiServices doctorService) {
        this.doctorService = doctorService;
    }

    @Tool(description = "Fetch available doctors from the external database based on the verified medical specialty.")
    public List<Map<String, Object>> fetchDoctorsBySpecialty(String specialty) {
        System.out.println("--- TOOL CALLED: Fetching doctors for specialty -> " + specialty + " ---");
        return doctorService.fetchDoctorsBySpecialty(specialty);
    }

}
