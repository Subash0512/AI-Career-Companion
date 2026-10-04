package com.example.AI.Resume.Maker.dto;

import java.util.ArrayList;
import java.util.List;

public class CareerGuidanceResponse {

    private String profileSummary;

    private List<String> recommendedRoles =
            new ArrayList<>();

    private List<String> skillGaps =
            new ArrayList<>();

    private List<String> skillsToLearn =
            new ArrayList<>();

    private List<String> recommendedProjects =
            new ArrayList<>();

    private List<String> actionPlan =
            new ArrayList<>();

    public CareerGuidanceResponse() {
    }

    public CareerGuidanceResponse(
            String profileSummary,
            List<String> recommendedRoles,
            List<String> skillGaps,
            List<String> skillsToLearn,
            List<String> recommendedProjects,
            List<String> actionPlan) {

        this.profileSummary = profileSummary;
        this.recommendedRoles = recommendedRoles;
        this.skillGaps = skillGaps;
        this.skillsToLearn = skillsToLearn;
        this.recommendedProjects = recommendedProjects;
        this.actionPlan = actionPlan;
    }

    public String getProfileSummary() {
        return profileSummary;
    }

    public void setProfileSummary(String profileSummary) {
        this.profileSummary = profileSummary;
    }

    public List<String> getRecommendedRoles() {
        return recommendedRoles;
    }

    public void setRecommendedRoles(
            List<String> recommendedRoles) {

        this.recommendedRoles = recommendedRoles;
    }

    public List<String> getSkillGaps() {
        return skillGaps;
    }

    public void setSkillGaps(List<String> skillGaps) {
        this.skillGaps = skillGaps;
    }

    public List<String> getSkillsToLearn() {
        return skillsToLearn;
    }

    public void setSkillsToLearn(
            List<String> skillsToLearn) {

        this.skillsToLearn = skillsToLearn;
    }

    public List<String> getRecommendedProjects() {
        return recommendedProjects;
    }

    public void setRecommendedProjects(
            List<String> recommendedProjects) {

        this.recommendedProjects = recommendedProjects;
    }

    public List<String> getActionPlan() {
        return actionPlan;
    }

    public void setActionPlan(
            List<String> actionPlan) {

        this.actionPlan = actionPlan;
    }
}