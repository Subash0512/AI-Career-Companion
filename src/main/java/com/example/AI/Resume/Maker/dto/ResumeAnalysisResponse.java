package com.example.AI.Resume.Maker.dto;

import java.util.List;

public class ResumeAnalysisResponse {

    private int score;
    private List<String> skills;
    private List<String> missingSkills;
    private List<String> strengths;
    private List<String> weaknesses;
    private List<String> suggestions;

    public ResumeAnalysisResponse() {
    }

    public ResumeAnalysisResponse(
            int score,
            List<String> skills,
            List<String> missingSkills,
            List<String> strengths,
            List<String> weaknesses,
            List<String> suggestions) {

        this.score = score;
        this.skills = skills;
        this.missingSkills = missingSkills;
        this.strengths = strengths;
        this.weaknesses = weaknesses;
        this.suggestions = suggestions;
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
}