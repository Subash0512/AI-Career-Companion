package com.example.AI.Resume.Maker.dto;

import java.util.ArrayList;
import java.util.List;

public class StudyPlanResponse {

    private String targetRole;

    private String currentLevel;

    private String overallGoal;

    private int estimatedWeeks;

    private List<StudyPlanTopic> topics =
            new ArrayList<>();

    private List<String> weeklyPlan =
            new ArrayList<>();

    private List<String> recommendedProjects =
            new ArrayList<>();

    public StudyPlanResponse() {
    }

    public StudyPlanResponse(
            String targetRole,
            String currentLevel,
            String overallGoal,
            int estimatedWeeks,
            List<StudyPlanTopic> topics,
            List<String> weeklyPlan,
            List<String> recommendedProjects) {

        this.targetRole = targetRole;
        this.currentLevel = currentLevel;
        this.overallGoal = overallGoal;
        this.estimatedWeeks = estimatedWeeks;
        this.topics = topics;
        this.weeklyPlan = weeklyPlan;
        this.recommendedProjects = recommendedProjects;
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

    public String getOverallGoal() {
        return overallGoal;
    }

    public void setOverallGoal(String overallGoal) {
        this.overallGoal = overallGoal;
    }

    public int getEstimatedWeeks() {
        return estimatedWeeks;
    }

    public void setEstimatedWeeks(int estimatedWeeks) {
        this.estimatedWeeks = estimatedWeeks;
    }

    public List<StudyPlanTopic> getTopics() {
        return topics;
    }

    public void setTopics(List<StudyPlanTopic> topics) {
        this.topics = topics;
    }

    public List<String> getWeeklyPlan() {
        return weeklyPlan;
    }

    public void setWeeklyPlan(List<String> weeklyPlan) {
        this.weeklyPlan = weeklyPlan;
    }

    public List<String> getRecommendedProjects() {
        return recommendedProjects;
    }

    public void setRecommendedProjects(
            List<String> recommendedProjects) {

        this.recommendedProjects = recommendedProjects;
    }
}