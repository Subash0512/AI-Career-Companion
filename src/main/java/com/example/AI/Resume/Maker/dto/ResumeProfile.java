package com.example.AI.Resume.Maker.dto;

import java.util.ArrayList;
import java.util.List;

public class ResumeProfile {

    private String email;
    private String phone;

    private List<String> skills = new ArrayList<>();
    private List<String> sections = new ArrayList<>();

    public ResumeProfile() {
    }

    public ResumeProfile(
            String email,
            String phone,
            List<String> skills,
            List<String> sections) {

        this.email = email;
        this.phone = phone;
        this.skills = skills;
        this.sections = sections;
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

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getSections() {
        return sections;
    }

    public void setSections(List<String> sections) {
        this.sections = sections;
    }
}