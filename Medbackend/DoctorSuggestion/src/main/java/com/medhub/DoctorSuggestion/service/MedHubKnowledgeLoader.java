package com.medhub.DoctorSuggestion.service;import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MedHubKnowledgeLoader {

    private final VectorStore vectorStore;

    public MedHubKnowledgeLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    public void loadSymptomKnowledgeBase() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = new ClassPathResource("SymptomsDoc.json").getInputStream();

            List<Map<String, Object>> symptoms = mapper.readValue(inputStream, new TypeReference<>() {});

            List<Document> documents = symptoms.stream().map(item -> {
                // Construct a rich string for embedding generation
                String content = String.format(
                        "Symptoms: %s. Recommended Specialty: %s. Urgency: %s. Age Group: %s. Clinical Rationale: %s",
                        item.get("symptom_cluster"),
                        item.get("required_specialty"),
                        item.get("urgency"),
                        item.get("age_group"),
                        item.get("clinical_rationale")
                );

                // Pass metadata for filtering during similarity search
                Map<String, Object> metadata = Map.of(
                        "specialty", item.get("required_specialty"),
                        "urgency", item.get("urgency"),
                        "source_id", item.get("id")
                );

                return new Document(content, metadata);
            }).collect(Collectors.toList());

            // Add documents to the Spring AI vector store
            vectorStore.add(documents);
            System.out.println("Successfully vectorized and loaded " + documents.size() + " symptom clinical profiles into Spring AI vector store!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
