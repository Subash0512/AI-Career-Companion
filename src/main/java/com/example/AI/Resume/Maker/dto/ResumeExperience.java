package com.example.AI.Resume.Maker.dto;

import java.util.ArrayList;
import java.util.List;

public class ResumeExperience {

    private String jobTitle;

    private String company;

    private String location;

    private String startDate;

    private String endDate;

    private List<String> responsibilities =
            new ArrayList<>();

    public ResumeExperience() {
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<String> getResponsibilities() {
        return responsibilities;
    }

    public void setResponsibilities(
            List<String> responsibilities) {

        this.responsibilities = responsibilities;
    }
}