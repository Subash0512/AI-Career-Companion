package com.example.AI.Resume.Maker.dto;

import java.util.ArrayList;
import java.util.List;

public class AiJobMatchFeedbackResponse {

    private String summary;

    private List<String> recommendations =
            new ArrayList<>();

    private List<String> improvements =
            new ArrayList<>();

    public AiJobMatchFeedbackResponse() {
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(
            List<String> recommendations) {

        this.recommendations = recommendations;
    }

    public List<String> getImprovements() {
        return improvements;
    }

    public void setImprovements(
            List<String> improvements) {

        this.improvements = improvements;
    }
}