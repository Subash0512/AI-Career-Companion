package com.example.AI.Resume.Maker.dto;

import java.util.ArrayList;
import java.util.List;

public class JobMatchResponse {

    private Long id;

    private String fileName;

    private String jobTitle;

    private int matchScore;

    private int requiredSkillScore;

    private int preferredSkillScore;

    private int keywordScore;

    private int relevanceScore;

    private int structureScore;

    private int contentScore;

    private List<String> matchedSkills = new ArrayList<>();

    private List<String> missingSkills = new ArrayList<>();

    private List<String> matchingKeywords = new ArrayList<>();

    private List<String> missingKeywords = new ArrayList<>();

    private List<String> matchedRequiredSkills =
            new ArrayList<>();

    private List<String> missingRequiredSkills =
            new ArrayList<>();

    private List<String> matchedPreferredSkills =
            new ArrayList<>();

    private List<String> missingPreferredSkills =
            new ArrayList<>();

    private String aiSummary;

    private List<String> aiRecommendations =
            new ArrayList<>();

    private List<String> aiImprovements =
            new ArrayList<>();

    public JobMatchResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public int getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }

    public int getRequiredSkillScore() {
        return requiredSkillScore;
    }

    public void setRequiredSkillScore(int requiredSkillScore) {
        this.requiredSkillScore = requiredSkillScore;
    }

    public int getPreferredSkillScore() {
        return preferredSkillScore;
    }

    public void setPreferredSkillScore(int preferredSkillScore) {
        this.preferredSkillScore = preferredSkillScore;
    }

    public int getKeywordScore() {
        return keywordScore;
    }

    public void setKeywordScore(int keywordScore) {
        this.keywordScore = keywordScore;
    }

    public int getRelevanceScore() {
        return relevanceScore;
    }

    public void setRelevanceScore(int relevanceScore) {
        this.relevanceScore = relevanceScore;
    }

    public int getStructureScore() {
        return structureScore;
    }

    public void setStructureScore(int structureScore) {
        this.structureScore = structureScore;
    }

    public int getContentScore() {
        return contentScore;
    }

    public void setContentScore(int contentScore) {
        this.contentScore = contentScore;
    }

    public List<String> getMatchedSkills() {
        return matchedSkills;
    }

    public void setMatchedSkills(List<String> matchedSkills) {
        this.matchedSkills = matchedSkills;
    }

    public List<String> getMissingSkills() {
        return missingSkills;
    }

    public void setMissingSkills(List<String> missingSkills) {
        this.missingSkills = missingSkills;
    }

    public List<String> getMatchingKeywords() {
        return matchingKeywords;
    }

    public void setMatchingKeywords(
            List<String> matchingKeywords) {

        this.matchingKeywords = matchingKeywords;
    }

    public List<String> getMissingKeywords() {
        return missingKeywords;
    }

    public void setMissingKeywords(
            List<String> missingKeywords) {

        this.missingKeywords = missingKeywords;
    }

    public List<String> getMatchedRequiredSkills() {
        return matchedRequiredSkills;
    }

    public void setMatchedRequiredSkills(
            List<String> matchedRequiredSkills) {

        this.matchedRequiredSkills =
                matchedRequiredSkills;
    }

    public List<String> getMissingRequiredSkills() {
        return missingRequiredSkills;
    }

    public void setMissingRequiredSkills(
            List<String> missingRequiredSkills) {

        this.missingRequiredSkills =
                missingRequiredSkills;
    }

    public List<String> getMatchedPreferredSkills() {
        return matchedPreferredSkills;
    }

    public void setMatchedPreferredSkills(
            List<String> matchedPreferredSkills) {

        this.matchedPreferredSkills =
                matchedPreferredSkills;
    }

    public List<String> getMissingPreferredSkills() {
        return missingPreferredSkills;
    }

    public void setMissingPreferredSkills(
            List<String> missingPreferredSkills) {

        this.missingPreferredSkills =
                missingPreferredSkills;
    }

    public String getAiSummary() {
        return aiSummary;
    }

    public void setAiSummary(String aiSummary) {
        this.aiSummary = aiSummary;
    }

    public List<String> getAiRecommendations() {
        return aiRecommendations;
    }

    public void setAiRecommendations(
            List<String> aiRecommendations) {

        this.aiRecommendations =
                aiRecommendations;
    }

    public List<String> getAiImprovements() {
        return aiImprovements;
    }

    public void setAiImprovements(
            List<String> aiImprovements) {

        this.aiImprovements =
                aiImprovements;
    }
}