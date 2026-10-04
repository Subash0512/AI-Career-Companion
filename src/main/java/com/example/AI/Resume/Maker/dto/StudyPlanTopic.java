package com.example.AI.Resume.Maker.dto;

public class StudyPlanTopic {

    private String topic;
    private String priority;
    private String reason;
    private String estimatedTime;
    private String practiceTask;

    public StudyPlanTopic() {
    }

    public StudyPlanTopic(
            String topic,
            String priority,
            String reason,
            String estimatedTime,
            String practiceTask) {

        this.topic = topic;
        this.priority = priority;
        this.reason = reason;
        this.estimatedTime = estimatedTime;
        this.practiceTask = practiceTask;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(String estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getPracticeTask() {
        return practiceTask;
    }

    public void setPracticeTask(String practiceTask) {
        this.practiceTask = practiceTask;
    }
}