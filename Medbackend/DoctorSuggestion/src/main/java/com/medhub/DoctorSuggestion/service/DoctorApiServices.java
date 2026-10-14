package com.medhub.DoctorSuggestion.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class DoctorApiServices {
    private final RestClient doctorServiceRestClient;

    public DoctorApiServices(RestClient doctorServiceRestClient) {
        this.doctorServiceRestClient = doctorServiceRestClient;
    }

    public List<Map<String, Object>> fetchDoctorsBySpecialty(String specialty) {
        try {
            // Makes a GET request to your separate doctor service endpoint, e.g., /api/doctors?specialty=Cardiologist
             List<Map<String, Object>> res=doctorServiceRestClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/v1/doctors")
                            .queryParam("specialty", specialty)
                            .build())
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

            if (res != null) {
                for (Map<String, Object> doctor : res) {
                    System.out.println(doctor);
                }
            }
             return res;
        } catch (Exception e) {
            System.err.println("Failed to fetch doctors from external service: " + e.toString());
            return Collections.emptyList();
        }
    }
}
