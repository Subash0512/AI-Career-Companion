package com.example.AI.Resume.Maker.dto;

import java.util.ArrayList;
import java.util.List;

public class ResumeProject {

    private String name;

    private String description;

    private List<String> technologies =
            new ArrayList<>();

    private List<String> contributions =
            new ArrayList<>();

    private String projectUrl;

    public ResumeProject() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(
            List<String> technologies) {

        this.technologies = technologies;
    }

    public List<String> getContributions() {
        return contributions;
    }

    public void setContributions(
            List<String> contributions) {

        this.contributions = contributions;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }
}