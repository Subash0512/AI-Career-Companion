package com.example.AI.Resume.Maker.dto;

import java.util.ArrayList;
import java.util.List;

public class InterviewPreparationResponse {

    private String targetRole;

    private List<InterviewQuestion> technicalQuestions =
            new ArrayList<>();

    private List<InterviewQuestion> projectQuestions =
            new ArrayList<>();

    private List<InterviewQuestion> hrQuestions =
            new ArrayList<>();

    public InterviewPreparationResponse() {
    }

    public InterviewPreparationResponse(
            String targetRole,
            List<InterviewQuestion> technicalQuestions,
            List<InterviewQuestion> projectQuestions,
            List<InterviewQuestion> hrQuestions) {

        this.targetRole = targetRole;
        this.technicalQuestions = technicalQuestions;
        this.projectQuestions = projectQuestions;
        this.hrQuestions = hrQuestions;
    }

    public String getTargetRole() {
        return targetRole;
    }

    public void setTargetRole(String targetRole) {
        this.targetRole = targetRole;
    }

    public List<InterviewQuestion> getTechnicalQuestions() {
        return technicalQuestions;
    }

    public void setTechnicalQuestions(
            List<InterviewQuestion> technicalQuestions) {

        this.technicalQuestions = technicalQuestions;
    }

    public List<InterviewQuestion> getProjectQuestions() {
        return projectQuestions;
    }

    public void setProjectQuestions(
            List<InterviewQuestion> projectQuestions) {

        this.projectQuestions = projectQuestions;
    }

    public List<InterviewQuestion> getHrQuestions() {
        return hrQuestions;
    }

    public void setHrQuestions(
            List<InterviewQuestion> hrQuestions) {

        this.hrQuestions = hrQuestions;
    }
}