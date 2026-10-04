package com.example.AI.Resume.Maker.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.example.AI.Resume.Maker.dto.ResumeProfile;

@Service
public class ResumeParser {

    /*
     * This is NOT the user's skill list.
     *
     * It is a generic vocabulary that the application knows how
     * to recognize when those technologies actually appear in
     * an uploaded resume.
     */
    private static final List<String> KNOWN_SKILLS = Arrays.asList(

            // Programming languages
            "java",
            "python",
            "c",
            "c++",
            "c#",
            "javascript",
            "typescript",
            "kotlin",
            "go",
            "rust",
            "php",

            // Backend
            "spring",
            "spring boot",
            "spring security",
            "hibernate",
            "jpa",
            "node.js",
            "express",
            "django",
            "flask",
            "asp.net",

            // Database
            "mysql",
            "postgresql",
            "sql",
            "mongodb",
            "oracle",
            "redis",

            // Frontend
            "html",
            "css",
            "react",
            "angular",
            "vue.js",
            "bootstrap",

            // DevOps / Cloud
            "git",
            "github",
            "gitlab",
            "maven",
            "gradle",
            "docker",
            "kubernetes",
            "aws",
            "azure",
            "google cloud",

            // APIs / Architecture
            "rest api",
            "rest",
            "microservices",
            "graphql",

            // Testing
            "junit",
            "mockito",
            "selenium"
    );

    public ResumeProfile parse(String resumeText) {

        ResumeProfile profile = new ResumeProfile();

        if (resumeText == null || resumeText.isBlank()) {
            return profile;
        }

        String normalizedText = normalizeText(resumeText);

        profile.setEmail(extractEmail(normalizedText));
        profile.setPhone(extractPhone(normalizedText));
        profile.setSkills(extractSkills(normalizedText));
        profile.setSections(extractSections(normalizedText));

        return profile;
    }

    private String normalizeText(String text) {

        return text
                .replace("\r", "\n")
                .replaceAll("[ \\t]+", " ")
                .replaceAll("\n{3,}", "\n")
                .trim();
    }

    private String extractEmail(String text) {

        String emailRegex =
                "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group();
        }

        return null;
    }

    private String extractPhone(String text) {

        String phoneRegex =
                "(?<!\\d)(?:\\+91[-.\\s]?)?[6-9]\\d{9}(?!\\d)";

        Pattern pattern = Pattern.compile(phoneRegex);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group();
        }

        return null;
    }

    private List<String> extractSkills(String text) {

        Set<String> foundSkills = new LinkedHashSet<>();

        for (String skill : KNOWN_SKILLS) {

            if (containsSkill(text, skill)) {
                foundSkills.add(skill);
            }
        }

        return new ArrayList<>(foundSkills);
    }

    private boolean containsSkill(
            String text,
            String skill) {

        String regex =
                "(?i)(?<![a-z0-9])"
                + Pattern.quote(skill)
                + "(?![a-z0-9])";

        return Pattern.compile(regex).matcher(text).find();
    }

    private List<String> extractSections(String text) {

        Set<String> sections = new LinkedHashSet<>();

        addSectionIfPresent(
                sections,
                text,
                "summary",
                "professional summary",
                "profile",
                "objective"
        );

        addSectionIfPresent(
                sections,
                text,
                "skills",
                "technical skills",
                "technical skill",
                "skills & technologies"
        );

        addSectionIfPresent(
                sections,
                text,
                "education",
                "academic",
                "academic background"
        );

        addSectionIfPresent(
                sections,
                text,
                "experience",
                "work experience",
                "professional experience",
                "employment"
        );

        addSectionIfPresent(
                sections,
                text,
                "projects",
                "project",
                "academic projects"
        );

        addSectionIfPresent(
                sections,
                text,
                "certifications",
                "certification",
                "certificates"
        );

        addSectionIfPresent(
                sections,
                text,
                "achievements",
                "achievement",
                "accomplishments"
        );

        addSectionIfPresent(
                sections,
                text,
                "languages",
                "language"
        );

        return new ArrayList<>(sections);
    }

    private void addSectionIfPresent(
            Set<String> sections,
            String text,
            String sectionName,
            String... alternatives) {

        if (containsSection(text, sectionName)) {
            sections.add(sectionName);
            return;
        }

        for (String alternative : alternatives) {

            if (containsSection(text, alternative)) {
                sections.add(sectionName);
                return;
            }
        }
    }

    private boolean containsSection(
            String text,
            String section) {

        String regex =
                "(?im)^\\s*"
                + Pattern.quote(section)
                + "\\s*[:\\-]?\\s*$";

        return Pattern.compile(regex).matcher(text).find();
    }
}