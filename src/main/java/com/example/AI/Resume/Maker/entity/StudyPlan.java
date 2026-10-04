package com.example.AI.Resume.Maker.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class StudyPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String targetRole;

    private String currentLevel;

    private int estimatedWeeks;

    private LocalDateTime generatedAt;

    @Lob
    private String overallGoal;

    @Lob
    private String topics;

    @Lob
    private String weeklyPlan;

    @Lob
    private String recommendedProjects;

    public StudyPlan() {
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

    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getEstimatedWeeks() {
        return estimatedWeeks;
    }

    public void setEstimatedWeeks(int estimatedWeeks) {
        this.estimatedWeeks = estimatedWeeks;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public String getOverallGoal() {
        return overallGoal;
    }

    public void setOverallGoal(String overallGoal) {
        this.overallGoal = overallGoal;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public String getWeeklyPlan() {
        return weeklyPlan;
    }

    public void setWeeklyPlan(String weeklyPlan) {
        this.weeklyPlan = weeklyPlan;
    }

    public String getRecommendedProjects() {
        return recommendedProjects;
    }

    public void setRecommendedProjects(String recommendedProjects) {
        this.recommendedProjects = recommendedProjects;
    }
}