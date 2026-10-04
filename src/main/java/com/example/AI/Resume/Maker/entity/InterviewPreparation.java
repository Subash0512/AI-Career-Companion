package com.example.AI.Resume.Maker.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class InterviewPreparation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String targetRole;

    private LocalDateTime generatedAt;

    @Lob
    private String technicalQuestions;

    @Lob
    private String projectQuestions;

    @Lob
    private String hrQuestions;

    public InterviewPreparation() {
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTargetRole() {
        return targetRole;
    }

    public void setTargetRole(String targetRole) {
        this.targetRole = targetRole;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public String getTechnicalQuestions() {
        return technicalQuestions;
    }

    public void setTechnicalQuestions(String technicalQuestions) {
        this.technicalQuestions = technicalQuestions;
    }

    public String getProjectQuestions() {
        return projectQuestions;
    }

    public void setProjectQuestions(String projectQuestions) {
        this.projectQuestions = projectQuestions;
    }

    public String getHrQuestions() {
        return hrQuestions;
    }

    public void setHrQuestions(String hrQuestions) {
        this.hrQuestions = hrQuestions;
    }
}