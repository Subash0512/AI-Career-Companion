package com.example.AI.Resume.Maker.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class ResumeAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private int score;

    @ElementCollection
    @CollectionTable(
            name = "resume_analysis_skills",
            joinColumns = @JoinColumn(name = "analysis_id")
    )
    @Column(name = "skill")
    private List<String> skills = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "resume_analysis_missing_skills",
            joinColumns = @JoinColumn(name = "analysis_id")
    )
    @Column(name = "missing_skill")
    private List<String> missingSkills = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "resume_analysis_strengths",
            joinColumns = @JoinColumn(name = "analysis_id")
    )
    @Column(name = "strength")
    private List<String> strengths = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "resume_analysis_weaknesses",
            joinColumns = @JoinColumn(name = "analysis_id")
    )
    @Column(name = "weakness")
    private List<String> weaknesses = new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "resume_analysis_suggestions",
            joinColumns = @JoinColumn(name = "analysis_id")
    )
    @Column(name = "suggestion")
    private List<String> suggestions = new ArrayList<>();

    private LocalDateTime analyzedAt;

    public ResumeAnalysis() {
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(List<String> missingSkills) {
        this.missingSkills = missingSkills;
    }

    public List<String> getStrengths() {
        return strengths;
    }

    public void setStrengths(List<String> strengths) {
        this.strengths = strengths;
    }

    public List<String> getWeaknesses() {
        return weaknesses;
    }

    public void setWeaknesses(List<String> weaknesses) {
        this.weaknesses = weaknesses;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    public LocalDateTime getAnalyzedAt() {
        return analyzedAt;
    }

    public void setAnalyzedAt(LocalDateTime analyzedAt) {
        this.analyzedAt = analyzedAt;
    }
}