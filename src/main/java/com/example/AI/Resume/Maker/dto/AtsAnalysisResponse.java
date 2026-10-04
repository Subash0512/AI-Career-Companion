package com.example.AI.Resume.Maker.dto;

import java.util.ArrayList;
import java.util.List;

public class AtsAnalysisResponse {

    private int score;

    private int requiredSkillScore;
    private int preferredSkillScore;
    private int keywordScore;
    private int relevanceScore;
    private int structureScore;
    private int contentScore;

    private List<String> matchedRequiredSkills = new ArrayList<>();
    private List<String> missingRequiredSkills = new ArrayList<>();

    private List<String> matchedPreferredSkills = new ArrayList<>();
    private List<String> missingPreferredSkills = new ArrayList<>();

    private List<String> matchedKeywords = new ArrayList<>();
    private List<String> missingKeywords = new ArrayList<>();

    private List<String> suggestions = new ArrayList<>();

    public AtsAnalysisResponse() {
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    public List<String> getMatchedRequiredSkills() {
        return matchedRequiredSkills;
    }

    public void setMatchedRequiredSkills(List<String> matchedRequiredSkills) {
        this.matchedRequiredSkills = matchedRequiredSkills;
    }

    public List<String> getMissingRequiredSkills() {
        return missingRequiredSkills;
    }

    public void setMissingRequiredSkills(List<String> missingRequiredSkills) {
        this.missingRequiredSkills = missingRequiredSkills;
    }

    public List<String> getMatchedPreferredSkills() {
        return matchedPreferredSkills;
    }

    public void setMatchedPreferredSkills(List<String> matchedPreferredSkills) {
        this.matchedPreferredSkills = matchedPreferredSkills;
    }

    public List<String> getMissingPreferredSkills() {
        return missingPreferredSkills;
    }

    public void setMissingPreferredSkills(List<String> missingPreferredSkills) {
        this.missingPreferredSkills = missingPreferredSkills;
    }

    public List<String> getMatchedKeywords() {
        return matchedKeywords;
    }

    public void setMatchedKeywords(List<String> matchedKeywords) {
        this.matchedKeywords = matchedKeywords;
    }

    public List<String> getMissingKeywords() {
        return missingKeywords;
    }

    public void setMissingKeywords(List<String> missingKeywords) {
        this.missingKeywords = missingKeywords;
    }

    public List<String> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }
}