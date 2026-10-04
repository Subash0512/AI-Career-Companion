package com.example.AI.Resume.Maker.dto;

public class ResumeBuilderResponse {

    private Long id;

    private String fileName;

    private String targetRole;

    private GeneratedResumeContent resume;

    private AtsAnalysisResponse atsAnalysis;

    private String atsMessage;

    public ResumeBuilderResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getTargetRole() {
        return targetRole;
    }

    public void setTargetRole(String targetRole) {
        this.targetRole = targetRole;
    }

    public GeneratedResumeContent getResume() {
        return resume;
    }

    public void setResume(
            GeneratedResumeContent resume) {

        this.resume = resume;
    }

    public AtsAnalysisResponse getAtsAnalysis() {
        return atsAnalysis;
    }

    public void setAtsAnalysis(
            AtsAnalysisResponse atsAnalysis) {

        this.atsAnalysis = atsAnalysis;
    }

    public String getAtsMessage() {
        return atsMessage;
    }

    public void setAtsMessage(String atsMessage) {
        this.atsMessage = atsMessage;
    }
}