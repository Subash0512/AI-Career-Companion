package com.example.AI.Resume.Maker.dto;

public class LearningResource {

    private String skill;

    private String platform;

    private String description;

    private String url;

    private String learningCost;

    private String credentialStatus;

    private String resourceType;

    public LearningResource() {
    }

    public LearningResource(
            String skill,
            String platform,
            String description,
            String url,
            String learningCost,
            String credentialStatus,
            String resourceType) {

        this.skill = skill;
        this.platform = platform;
        this.description = description;
        this.url = url;
        this.learningCost = learningCost;
        this.credentialStatus = credentialStatus;
        this.resourceType = resourceType;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description) {

        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLearningCost() {
        return learningCost;
    }

    public void setLearningCost(
            String learningCost) {

        this.learningCost = learningCost;
    }

    public String getCredentialStatus() {
        return credentialStatus;
    }

    public void setCredentialStatus(
            String credentialStatus) {

        this.credentialStatus = credentialStatus;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(
            String resourceType) {

        this.resourceType = resourceType;
    }
}