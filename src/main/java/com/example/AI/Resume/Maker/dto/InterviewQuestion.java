package com.example.AI.Resume.Maker.dto;

public class InterviewQuestion {

    private String question;
    private String category;
    private String difficulty;
    private String expectedAnswer;

    public InterviewQuestion() {
    }

    public InterviewQuestion(
            String question,
            String category,
            String difficulty,
            String expectedAnswer) {

        this.question = question;
        this.category = category;
        this.difficulty = difficulty;
        this.expectedAnswer = expectedAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getExpectedAnswer() {
        return expectedAnswer;
    }

    public void setExpectedAnswer(String expectedAnswer) {
        this.expectedAnswer = expectedAnswer;
    }
}