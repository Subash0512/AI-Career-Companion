package com.example.AI.Resume.Maker.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class GeneratedResume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String targetRole;

    private int atsScore;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String resumeContent;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String jobDescription;

    private LocalDateTime generatedAt;

    public GeneratedResume() {
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getTargetRole() {
        return targetRole;
    }

    public int getAtsScore() {
        return atsScore;
    }

    public String getResumeContent() {
        return resumeContent;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setTargetRole(String targetRole) {
        this.targetRole = targetRole;
    }

    public void setAtsScore(int atsScore) {
        this.atsScore = atsScore;
    }

    public void setResumeContent(
            String resumeContent) {

        this.resumeContent = resumeContent;
    }

    public void setJobDescription(
            String jobDescription) {

        this.jobDescription = jobDescription;
    }

    public void setGeneratedAt(
            LocalDateTime generatedAt) {

        this.generatedAt = generatedAt;
    }
}