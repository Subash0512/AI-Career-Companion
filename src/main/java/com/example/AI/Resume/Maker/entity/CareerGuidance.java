package com.example.AI.Resume.Maker.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class CareerGuidance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String targetRole;

    private LocalDateTime generatedAt;

    @Lob
    private String profileSummary;

    @Lob
    private String recommendedRoles;

    @Lob
    private String skillGaps;

    @Lob
    private String skillsToLearn;

    @Lob
    private String recommendedProjects;

    @Lob
    private String actionPlan;

    public CareerGuidance() {
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

    public String getProfileSummary() {
        return profileSummary;
    }

    public void setProfileSummary(String profileSummary) {
        this.profileSummary = profileSummary;
    }

    public String getRecommendedRoles() {
        return recommendedRoles;
    }

    public void setRecommendedRoles(String recommendedRoles) {
        this.recommendedRoles = recommendedRoles;
    }

    public String getSkillGaps() {
        return skillGaps;
    }

    public void setSkillGaps(String skillGaps) {
        this.skillGaps = skillGaps;
    }

    public String getSkillsToLearn() {
        return skillsToLearn;
    }

    public void setSkillsToLearn(String skillsToLearn) {
        this.skillsToLearn = skillsToLearn;
    }

    public String getRecommendedProjects() {
        return recommendedProjects;
    }

    public void setRecommendedProjects(String recommendedProjects) {
        this.recommendedProjects = recommendedProjects;
    }

    public String getActionPlan() {
        return actionPlan;
    }

    public void setActionPlan(String actionPlan) {
        this.actionPlan = actionPlan;
    }
}