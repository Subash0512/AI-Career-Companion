package com.example.AI.Resume.Maker.dto;

import java.util.ArrayList;
import java.util.List;

public class GeneratedResumeContent {

    private String fullName;

    private String email;

    private String phone;

    private String location;

    private String linkedin;

    private String github;

    private String portfolio;

    private String professionalSummary;

    private List<String> skills =
            new ArrayList<>();

    private List<ResumeExperience> experience =
            new ArrayList<>();

    private List<ResumeProject> projects =
            new ArrayList<>();

    private List<ResumeEducation> education =
            new ArrayList<>();

    private List<String> certifications =
            new ArrayList<>();

    private List<String> achievements =
            new ArrayList<>();

    private List<String> languages =
            new ArrayList<>();

    public GeneratedResumeContent() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }

    public String getGithub() {
        return github;
    }

    public void setGithub(String github) {
        this.github = github;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public String getProfessionalSummary() {
        return professionalSummary;
    }

    public void setProfessionalSummary(
            String professionalSummary) {

        this.professionalSummary =
                professionalSummary;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<ResumeExperience> getExperience() {
        return experience;
    }

    public void setExperience(
            List<ResumeExperience> experience) {

        this.experience = experience;
    }

    public List<ResumeProject> getProjects() {
        return projects;
    }

    public void setProjects(
            List<ResumeProject> projects) {

        this.projects = projects;
    }

    public List<ResumeEducation> getEducation() {
        return education;
    }

    public void setEducation(
            List<ResumeEducation> education) {

        this.education = education;
    }

    public List<String> getCertifications() {
        return certifications;
    }

    public void setCertifications(
            List<String> certifications) {

        this.certifications = certifications;
    }

    public List<String> getAchievements() {
        return achievements;
    }

    public void setAchievements(
            List<String> achievements) {

        this.achievements = achievements;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(
            List<String> languages) {

        this.languages = languages;
    }
}