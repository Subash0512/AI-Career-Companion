package com.example.AI.Resume.Maker.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.example.AI.Resume.Maker.dto.ResumeAnalysisResponse;

@Service
public class RuleBasedAnalyzer {

    private static final List<String> TECHNICAL_SKILLS = Arrays.asList(
            "java",
            "python",
            "c",
            "c++",
            "javascript",
            "typescript",
            "spring",
            "spring boot",
            "hibernate",
            "jpa",
            "mysql",
            "sql",
            "mongodb",
            "html",
            "css",
            "react",
            "angular",
            "node.js",
            "git",
            "github",
            "docker",
            "aws",
            "azure"
    );

    public ResumeAnalysisResponse analyze(String resumeText) {

        if (resumeText == null || resumeText.isBlank()) {
            return createEmptyResponse();
        }

        String text = resumeText.toLowerCase();

        List<String> skills = findSkills(text);
        List<String> missingSkills = findMissingSkills(skills);

        int score = calculateScore(text, skills);

        List<String> strengths = findStrengths(text, skills);
        List<String> weaknesses = findWeaknesses(text, skills);
        List<String> suggestions = generateSuggestions(text, skills);

        return new ResumeAnalysisResponse(
                score,
                skills,
                missingSkills,
                strengths,
                weaknesses,
                suggestions
        );
    }

    private List<String> findSkills(String text) {

        List<String> foundSkills = new ArrayList<>();

        for (String skill : TECHNICAL_SKILLS) {

            if (text.contains(skill)) {
                foundSkills.add(skill);
            }
        }

        return foundSkills;
    }

    private List<String> findMissingSkills(List<String> skills) {

        List<String> importantSkills = Arrays.asList(
                "java",
                "spring boot",
                "sql",
                "git",
                "docker"
        );

        List<String> missingSkills = new ArrayList<>();

        for (String skill : importantSkills) {

            if (!skills.contains(skill)) {
                missingSkills.add(skill);
            }
        }

        return missingSkills;
    }

    private int calculateScore(String text, List<String> skills) {

        int score = 0;

        // Contact information
        if (containsEmail(text)) {
            score += 10;
        }

        if (containsPhone(text)) {
            score += 10;
        }

        // Resume sections
        if (containsAny(text, "education", "academic")) {
            score += 10;
        }

        if (containsAny(text, "experience", "work experience")) {
            score += 15;
        }

        if (containsAny(text, "project", "projects")) {
            score += 15;
        }

        if (containsAny(text, "summary", "objective", "profile")) {
            score += 10;
        }

        // Technical skills
        if (!skills.isEmpty()) {
            score += 20;
        }

        // More skills = slightly better score
        if (skills.size() >= 5) {
            score += 10;
        }

        return Math.min(score, 100);
    }

    private List<String> findStrengths(
            String text,
            List<String> skills) {

        List<String> strengths = new ArrayList<>();

        if (containsEmail(text) && containsPhone(text)) {
            strengths.add("Contact information is available");
        }

        if (containsAny(text, "education", "academic")) {
            strengths.add("Education section is present");
        }

        if (containsAny(text, "project", "projects")) {
            strengths.add("Projects section is present");
        }

        if (containsAny(text, "experience", "work experience")) {
            strengths.add("Experience section is present");
        }

        if (skills.size() >= 5) {
            strengths.add("Good number of technical skills detected");
        } else if (!skills.isEmpty()) {
            strengths.add("Technical skills are present");
        }

        return strengths;
    }

    private List<String> findWeaknesses(
            String text,
            List<String> skills) {

        List<String> weaknesses = new ArrayList<>();

        if (!containsEmail(text)) {
            weaknesses.add("Email address was not detected");
        }

        if (!containsPhone(text)) {
            weaknesses.add("Phone number was not detected");
        }

        if (!containsAny(text, "education", "academic")) {
            weaknesses.add("Education section was not detected");
        }

        if (!containsAny(text, "project", "projects")) {
            weaknesses.add("Projects section was not detected");
        }

        if (!containsAny(text, "experience", "work experience")) {
            weaknesses.add("Experience section was not detected");
        }

        if (skills.isEmpty()) {
            weaknesses.add("No technical skills were detected");
        }

        return weaknesses;
    }

    private List<String> generateSuggestions(
            String text,
            List<String> skills) {

        List<String> suggestions = new ArrayList<>();

        if (!containsAny(text, "summary", "objective", "profile")) {
            suggestions.add("Consider adding a professional summary");
        }

        if (!containsAny(text, "project", "projects")) {
            suggestions.add("Add a projects section with measurable results");
        }

        if (!containsAny(text, "experience", "work experience")) {
            suggestions.add("Add relevant experience or internship details if applicable");
        }

        if (skills.size() < 5) {
            suggestions.add("Add more relevant technical skills that you actually know");
        }

        if (!skills.contains("git") && !skills.contains("github")) {
            suggestions.add("Mention Git or GitHub experience if applicable");
        }

        if (!skills.contains("docker")) {
            suggestions.add("Consider learning Docker for backend development");
        }

        return suggestions;
    }

    private boolean containsEmail(String text) {

        String emailRegex =
                "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";

        return Pattern.compile(emailRegex)
                .matcher(text)
                .find();
    }

    private boolean containsPhone(String text) {

        String phoneRegex = "\\b\\d{10}\\b";

        return Pattern.compile(phoneRegex)
                .matcher(text)
                .find();
    }

    private boolean containsAny(
            String text,
            String... keywords) {

        for (String keyword : keywords) {

            if (text.contains(keyword.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

    private ResumeAnalysisResponse createEmptyResponse() {

        return new ResumeAnalysisResponse(
                0,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                List.of("Resume text could not be read"),
                List.of("Upload a readable text-based PDF")
        );
    }
}