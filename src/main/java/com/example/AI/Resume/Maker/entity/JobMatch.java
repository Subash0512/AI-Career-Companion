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
import jakarta.persistence.Lob;

@Entity
public class JobMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String aiSummary;

    private LocalDateTime matchedAt;

    @ElementCollection
    @CollectionTable(
            name = "job_match_matched_skills",
            joinColumns = @JoinColumn(
                    name = "job_match_id"
            )
    )
    @Column(name = "skill")
    private List<String> matchedSkills =
            new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "job_match_missing_skills",
            joinColumns = @JoinColumn(
                    name = "job_match_id"
            )
    )
    @Column(name = "skill")
    private List<String> missingSkills =
            new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "job_match_keywords",
            joinColumns = @JoinColumn(
                    name = "job_match_id"
            )
    )
    @Column(name = "keyword")
    private List<String> matchingKeywords =
            new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "job_match_missing_keywords",
            joinColumns = @JoinColumn(
                    name = "job_match_id"
            )
    )
    @Column(name = "keyword")
    private List<String> missingKeywords =
            new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "job_match_matched_required_skills",
            joinColumns = @JoinColumn(
                    name = "job_match_id"
            )
    )
    @Column(name = "skill")
    private List<String> matchedRequiredSkills =
            new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "job_match_missing_required_skills",
            joinColumns = @JoinColumn(
                    name = "job_match_id"
            )
    )
    @Column(name = "skill")
    private List<String> missingRequiredSkills =
            new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "job_match_matched_preferred_skills",
            joinColumns = @JoinColumn(
                    name = "job_match_id"
            )
    )
    @Column(name = "skill")
    private List<String> matchedPreferredSkills =
            new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "job_match_missing_preferred_skills",
            joinColumns = @JoinColumn(
                    name = "job_match_id"
            )
    )
    @Column(name = "skill")
    private List<String> missingPreferredSkills =
            new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "job_match_recommendations",
            joinColumns = @JoinColumn(
                    name = "job_match_id"
            )
    )
    @Column(name = "recommendation")
    private List<String> aiRecommendations =
            new ArrayList<>();

    @ElementCollection
    @CollectionTable(
            name = "job_match_improvements",
            joinColumns = @JoinColumn(
                    name = "job_match_id"
            )
    )
    @Column(name = "improvement")
    private List<String> aiImprovements =
            new ArrayList<>();

    public JobMatch() {
    }

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public int getMatchScore() {
        return matchScore;
    }

    public int getRequiredSkillScore() {
        return requiredSkillScore;
    }

    public int getPreferredSkillScore() {
        return preferredSkillScore;
    }

    public int getKeywordScore() {
        return keywordScore;
    }

    public int getRelevanceScore() {
        return relevanceScore;
    }

    public int getStructureScore() {
        return structureScore;
    }

    public int getContentScore() {
        return contentScore;
    }

    public String getAiSummary() {
        return aiSummary;
    }

    public LocalDateTime getMatchedAt() {
        return matchedAt;
    }

    public List<String> getMatchedSkills() {
        return matchedSkills;
    }

    public List<String> getMissingSkills() {
        return missingSkills;
    }

    public List<String> getMatchingKeywords() {
        return matchingKeywords;
    }

    public List<String> getMissingKeywords() {
        return missingKeywords;
    }

    public List<String> getMatchedRequiredSkills() {
        return matchedRequiredSkills;
    }

    public List<String> getMissingRequiredSkills() {
        return missingRequiredSkills;
    }

    public List<String> getMatchedPreferredSkills() {
        return matchedPreferredSkills;
    }

    public List<String> getMissingPreferredSkills() {
        return missingPreferredSkills;
    }

    public List<String> getAiRecommendations() {
        return aiRecommendations;
    }

    public List<String> getAiImprovements() {
        return aiImprovements;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setMatchScore(int matchScore) {
        this.matchScore = matchScore;
    }

    public void setRequiredSkillScore(
            int requiredSkillScore) {

        this.requiredSkillScore =
                requiredSkillScore;
    }

    public void setPreferredSkillScore(
            int preferredSkillScore) {

        this.preferredSkillScore =
                preferredSkillScore;
    }

    public void setKeywordScore(int keywordScore) {
        this.keywordScore = keywordScore;
    }

    public void setRelevanceScore(int relevanceScore) {
        this.relevanceScore = relevanceScore;
    }

    public void setStructureScore(int structureScore) {
        this.structureScore = structureScore;
    }

    public void setContentScore(int contentScore) {
        this.contentScore = contentScore;
    }

    public void setAiSummary(String aiSummary) {
        this.aiSummary = aiSummary;
    }

    public void setMatchedAt(
            LocalDateTime matchedAt) {

        this.matchedAt = matchedAt;
    }

    public void setMatchedSkills(
            List<String> matchedSkills) {

        this.matchedSkills = matchedSkills;
    }

    public void setMissingSkills(
            List<String> missingSkills) {

        this.missingSkills = missingSkills;
    }

    public void setMatchingKeywords(
            List<String> matchingKeywords) {

        this.matchingKeywords =
                matchingKeywords;
    }

    public void setMissingKeywords(
            List<String> missingKeywords) {

        this.missingKeywords =
                missingKeywords;
    }

    public void setMatchedRequiredSkills(
            List<String> matchedRequiredSkills) {

        this.matchedRequiredSkills =
                matchedRequiredSkills;
    }

    public void setMissingRequiredSkills(
            List<String> missingRequiredSkills) {

        this.missingRequiredSkills =
                missingRequiredSkills;
    }

    public void setMatchedPreferredSkills(
            List<String> matchedPreferredSkills) {

        this.matchedPreferredSkills =
                matchedPreferredSkills;
    }

    public void setMissingPreferredSkills(
            List<String> missingPreferredSkills) {

        this.missingPreferredSkills =
                missingPreferredSkills;
    }

    public void setAiRecommendations(
            List<String> aiRecommendations) {

        this.aiRecommendations =
                aiRecommendations;
    }

    public void setAiImprovements(
            List<String> aiImprovements) {

        this.aiImprovements =
                aiImprovements;
    }
}