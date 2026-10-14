package com.medhub.RagChat.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "parent_chunks")
public class ParentChunk {

    @Id
    private String id;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    private String medicalCategory;
    private String sourceFile;

    public ParentChunk(){};

    public ParentChunk(String id, String content, String medicalCategory, String sourceFile) {
        this.id = id;
        this.content = content;
        this.medicalCategory = medicalCategory;
        this.sourceFile = sourceFile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMedicalCategory() {
        return medicalCategory;
    }

    public void setMedicalCategory(String medicalCategory) {
        this.medicalCategory = medicalCategory;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }
}
