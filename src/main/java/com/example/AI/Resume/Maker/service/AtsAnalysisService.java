package com.example.AI.Resume.Maker.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.example.AI.Resume.Maker.dto.AtsAnalysisResponse;

@Service
public class AtsAnalysisService {

    private static final int REQUIRED_SKILL_MAX = 40;
    private static final int PREFERRED_SKILL_MAX = 10;
    private static final int KEYWORD_MAX = 20;
    private static final int RELEVANCE_MAX = 10;
    private static final int STRUCTURE_MAX = 10;
    private static final int CONTENT_MAX = 10;

    private static final List<String> KNOWN_SKILLS = List.of(
            "java",
            "python",
            "c",
            "c++",
            "c#",
            "javascript",
            "typescript",
            "html",
            "css",
            "bootstrap",
            "react",
            "angular",
            "node.js",
            "nodejs",
            "spring",
            "spring boot",
            "spring security",
            "spring data jpa",
            "hibernate",
            "jpa",
            "rest api",
            "restful api",
            "microservices",
            "mysql",
            "postgresql",
            "mongodb",
            "sql",
            "oracle",
            "redis",
            "docker",
            "kubernetes",
            "aws",
            "azure",
            "gcp",
            "git",
            "github",
            "gitlab",
            "maven",
            "gradle",
            "linux",
            "jenkins",
            "ci/cd",
            "junit",
            "mockito",
            "postman",
            "swagger",
            "api testing",
            "data structures",
            "algorithms",
            "machine learning",
            "deep learning",
            "artificial intelligence",
            "data analysis",
            "pandas",
            "numpy",
            "tensorflow",
            "pytorch"
    );

    private static final List<String> REQUIRED_INDICATORS = List.of(
            "required",
            "must have",
            "must-have",
            "mandatory",
            "essential",
            "strong knowledge",
            "strong experience",
            "should have",
            "need to have",
            "required skills",
            "requirements"
    );

    private static final List<String> PREFERRED_INDICATORS = List.of(
            "preferred",
            "nice to have",
            "nice-to-have",
            "plus",
            "bonus",
            "desirable",
            "preferred skills",
            "good to have"
    );

    private static final List<String> TECHNICAL_KEYWORDS = List.of(
            "backend",
            "frontend",
            "api",
            "rest",
            "database",
            "cloud",
            "security",
            "authentication",
            "authorization",
            "testing",
            "unit testing",
            "integration testing",
            "debugging",
            "deployment",
            "architecture",
            "scalable",
            "performance",
            "agile",
            "scrum",
            "ci/cd",
            "version control",
            "software development",
            "problem solving",
            "communication",
            "teamwork",
            "object oriented",
            "object-oriented",
            "development lifecycle"
    );

    private static final Set<String> STOP_WORDS = Set.of(
            "the",
            "and",
            "for",
            "with",
            "that",
            "this",
            "from",
            "have",
            "has",
            "will",
            "your",
            "you",
            "our",
            "are",
            "was",
            "were",
            "into",
            "using",
            "use",
            "work",
            "working",
            "role",
            "job",
            "team",
            "teams",
            "years",
            "year",
            "experience",
            "skills",
            "skill",
            "knowledge",
            "ability",
            "strong",
            "good",
            "excellent",
            "required",
            "preferred",
            "including",
            "such",
            "their",
            "they",
            "them",
            "about",
            "more",
            "than",
            "within",
            "across",
            "through",
            "should"
    );

    public AtsAnalysisResponse analyze(
            String resumeText,
            String jobDescription) {

        return analyze(
                resumeText,
                "",
                jobDescription
        );
    }

    public AtsAnalysisResponse analyze(
            String resumeText,
            String jobTitle,
            String jobDescription) {

        validateInput(resumeText, jobDescription);

        String resume = normalize(resumeText);
        String job = normalize(jobDescription);
        String title = normalize(jobTitle);

        List<String> resumeSkills =
                extractSkills(resume);

        List<String> jobSkills =
                extractSkills(job);

        List<String> requiredSkills =
                new ArrayList<>();

        List<String> preferredSkills =
                new ArrayList<>();

        for (String skill : jobSkills) {

            if (isPreferredSkill(job, skill)) {
                preferredSkills.add(skill);
            } else {
                requiredSkills.add(skill);
            }
        }

        requiredSkills =
                removeDuplicates(requiredSkills);

        preferredSkills =
                removeDuplicates(preferredSkills);

        List<String> matchedRequiredSkills =
                findMatched(requiredSkills, resumeSkills);

        List<String> missingRequiredSkills =
                findMissing(requiredSkills, resumeSkills);

        List<String> matchedPreferredSkills =
                findMatched(preferredSkills, resumeSkills);

        List<String> missingPreferredSkills =
                findMissing(preferredSkills, resumeSkills);

        List<String> keywords =
                extractKeywords(job);

        List<String> matchedKeywords =
                new ArrayList<>();

        List<String> missingKeywords =
                new ArrayList<>();

        for (String keyword : keywords) {

            if (containsTerm(resume, keyword)) {
                matchedKeywords.add(keyword);
            } else {
                missingKeywords.add(keyword);
            }
        }

        int requiredSkillScore =
                calculatePercentageScore(
                        matchedRequiredSkills.size(),
                        requiredSkills.size(),
                        REQUIRED_SKILL_MAX
                );

        int preferredSkillScore =
                calculatePercentageScore(
                        matchedPreferredSkills.size(),
                        preferredSkills.size(),
                        PREFERRED_SKILL_MAX
                );

        int keywordScore =
                calculatePercentageScore(
                        matchedKeywords.size(),
                        keywords.size(),
                        KEYWORD_MAX
                );

        int relevanceScore =
                calculateRelevanceScore(
                        resume,
                        title,
                        requiredSkills,
                        preferredSkills
                );

        int structureScore =
                calculateStructureScore(resume);

        int contentScore =
                calculateContentScore(resume);

        int totalScore =
                requiredSkillScore
                + preferredSkillScore
                + keywordScore
                + relevanceScore
                + structureScore
                + contentScore;

        totalScore =
                clamp(totalScore, 0, 100);

        List<String> suggestions =
                buildSuggestions(
                        missingRequiredSkills,
                        missingPreferredSkills,
                        missingKeywords,
                        resume
                );

        AtsAnalysisResponse response =
                new AtsAnalysisResponse();

        response.setScore(totalScore);

        response.setRequiredSkillScore(
                requiredSkillScore
        );

        response.setPreferredSkillScore(
                preferredSkillScore
        );

        response.setKeywordScore(
                keywordScore
        );

        response.setRelevanceScore(
                relevanceScore
        );

        response.setStructureScore(
                structureScore
        );

        response.setContentScore(
                contentScore
        );

        response.setMatchedRequiredSkills(
                matchedRequiredSkills
        );

        response.setMissingRequiredSkills(
                missingRequiredSkills
        );

        response.setMatchedPreferredSkills(
                matchedPreferredSkills
        );

        response.setMissingPreferredSkills(
                missingPreferredSkills
        );

        response.setMatchedKeywords(
                matchedKeywords
        );

        response.setMissingKeywords(
                missingKeywords
        );

        response.setSuggestions(
                suggestions
        );

        return response;
    }

    private void validateInput(
            String resumeText,
            String jobDescription) {

        if (resumeText == null ||
                resumeText.isBlank()) {

            throw new IllegalArgumentException(
                    "Resume text cannot be empty."
            );
        }

        if (jobDescription == null ||
                jobDescription.isBlank()) {

            throw new IllegalArgumentException(
                    "Job description cannot be empty."
            );
        }
    }

    private List<String> extractSkills(
            String text) {

        List<String> foundSkills =
                new ArrayList<>();

        for (String skill : KNOWN_SKILLS) {

            if (containsTerm(text, skill)) {
                foundSkills.add(skill);
            }
        }

        return removeDuplicates(foundSkills);
    }

    private boolean isPreferredSkill(
            String jobDescription,
            String skill) {

        String lowerJob =
                jobDescription.toLowerCase(Locale.ROOT);

        String[] sections =
                lowerJob.split("[\\n.!?;]+");

        for (String section : sections) {

            String cleaned =
                    section.trim();

            if (!containsTerm(cleaned, skill)) {
                continue;
            }

            for (String indicator :
                    PREFERRED_INDICATORS) {

                if (cleaned.contains(indicator)) {
                    return true;
                }
            }

            for (String indicator :
                    REQUIRED_INDICATORS) {

                if (cleaned.contains(indicator)) {
                    return false;
                }
            }
        }

        /*
         * A technical skill listed without an explicit
         * "preferred" indicator is treated as important.
         */
        return false;
    }

    private List<String> extractKeywords(
            String jobDescription) {

        Set<String> keywords =
                new LinkedHashSet<>();

        for (String keyword :
                TECHNICAL_KEYWORDS) {

            if (containsTerm(
                    jobDescription,
                    keyword)) {

                keywords.add(keyword);
            }
        }

        /*
         * Add meaningful single technical words found
         * in the job description.
         */
        String cleaned =
                jobDescription
                        .toLowerCase(Locale.ROOT)
                        .replaceAll(
                                "[^a-z0-9+#.\\-/ ]",
                                " "
                        );

        String[] words =
                cleaned.split("\\s+");

        for (String word : words) {

            if (word.length() < 5) {
                continue;
            }

            if (STOP_WORDS.contains(word)) {
                continue;
            }

            if (isGenericKeyword(word)) {
                continue;
            }

            keywords.add(word);

            if (keywords.size() >= 20) {
                break;
            }
        }

        return new ArrayList<>(keywords);
    }

    private boolean isGenericKeyword(
            String word) {

        return switch (word) {

            case "developer",
                 "development",
                 "software",
                 "application",
                 "applications",
                 "company",
                 "candidate",
                 "position",
                 "responsibilities",
                 "qualifications",
                 "requirement",
                 "requirements",
                 "environment",
                 "solutions",
                 "projects",
                 "project",
                 "technical",
                 "professional",
                 "ability",
                 "including",
                 "support",
                 "business" -> true;

            default -> false;
        };
    }

    private int calculatePercentageScore(
            int matched,
            int total,
            int maximum) {

        if (total == 0) {
            return maximum;
        }

        double percentage =
                (double) matched / total;

        return (int) Math.round(
                percentage * maximum
        );
    }

    private int calculateRelevanceScore(
            String resume,
            String jobTitle,
            List<String> requiredSkills,
            List<String> preferredSkills) {

        int score = 0;

        /*
         * First check target role words.
         */
        if (jobTitle != null &&
                !jobTitle.isBlank()) {

            String[] titleWords =
                    jobTitle.split("\\s+");

            int validTitleWords = 0;
            int matchedTitleWords = 0;

            for (String word : titleWords) {

                String cleaned =
                        word.replaceAll(
                                "[^a-z0-9]",
                                ""
                        );

                if (cleaned.length() < 3 ||
                        STOP_WORDS.contains(cleaned)) {
                    continue;
                }

                validTitleWords++;

                if (resume.contains(cleaned)) {
                    matchedTitleWords++;
                }
            }

            if (validTitleWords > 0) {

                score += (int) Math.round(
                        ((double) matchedTitleWords
                                / validTitleWords) * 5
                );
            }
        }

        /*
         * Remaining relevance points come from
         * the actual job skills.
         */
        List<String> allJobSkills =
                new ArrayList<>();

        allJobSkills.addAll(requiredSkills);
        allJobSkills.addAll(preferredSkills);

        if (!allJobSkills.isEmpty()) {

            int matched = 0;

            for (String skill : allJobSkills) {

                if (containsTerm(resume, skill)) {
                    matched++;
                }
            }

            score += (int) Math.round(
                    ((double) matched
                            / allJobSkills.size()) * 5
            );
        } else {

            score += 5;
        }

        return clamp(score, 0, RELEVANCE_MAX);
    }

    private int calculateStructureScore(
            String resume) {

        int score = 0;

        if (containsAny(
                resume,
                "summary",
                "professional summary",
                "profile",
                "objective")) {

            score += 2;
        }

        if (containsAny(
                resume,
                "skills",
                "technical skills",
                "technical skill")) {

            score += 2;
        }

        if (containsAny(
                resume,
                "education",
                "academic")) {

            score += 2;
        }

        if (containsAny(
                resume,
                "experience",
                "work experience",
                "internship")) {

            score += 2;
        }

        if (containsAny(
                resume,
                "projects",
                "project")) {

            score += 2;
        }

        return clamp(
                score,
                0,
                STRUCTURE_MAX
        );
    }

    private int calculateContentScore(
            String resume) {

        int score = 0;

        if (resume.length() >= 500) {
            score += 3;
        }

        if (resume.length() >= 1000) {
            score += 2;
        }

        if (containsAny(
                resume,
                "developed",
                "implemented",
                "created",
                "built",
                "designed",
                "improved",
                "optimized",
                "integrated")) {

            score += 2;
        }

        if (containsAny(
                resume,
                "%",
                "increased",
                "reduced",
                "saved",
                "performance",
                "users")) {

            score += 2;
        }

        if (containsAny(
                resume,
                "github",
                "linkedin",
                "portfolio")) {

            score += 1;
        }

        return clamp(
                score,
                0,
                CONTENT_MAX
        );
    }

    private List<String> buildSuggestions(
            List<String> missingRequiredSkills,
            List<String> missingPreferredSkills,
            List<String> missingKeywords,
            String resume) {

        List<String> suggestions =
                new ArrayList<>();

        if (!missingRequiredSkills.isEmpty()) {

            suggestions.add(
                    "Missing required skills were detected. Only add a skill to the resume if you genuinely possess that skill."
            );
        }

        if (!missingPreferredSkills.isEmpty()) {

            suggestions.add(
                    "Consider learning the preferred skills that are relevant to your target role."
            );
        }

        if (!missingKeywords.isEmpty()) {

            suggestions.add(
                    "Use relevant job-description terminology naturally where it accurately describes your existing experience."
            );
        }

        if (!containsAny(
                resume,
                "summary",
                "professional summary",
                "profile",
                "objective")) {

            suggestions.add(
                    "Add a concise professional summary targeted to the job."
            );
        }

        if (!containsAny(
                resume,
                "projects",
                "project")) {

            suggestions.add(
                    "Add relevant projects that demonstrate job-related skills."
            );
        }

        return suggestions;
    }

    private List<String> findMatched(
            List<String> jobSkills,
            List<String> resumeSkills) {

        return jobSkills.stream()
                .filter(resumeSkills::contains)
                .toList();
    }

    private List<String> findMissing(
            List<String> jobSkills,
            List<String> resumeSkills) {

        return jobSkills.stream()
                .filter(skill ->
                        !resumeSkills.contains(skill))
                .toList();
    }

    private List<String> removeDuplicates(
            List<String> values) {

        return new ArrayList<>(
                new LinkedHashSet<>(values)
        );
    }

    private boolean containsTerm(
            String text,
            String term) {

        if (text == null ||
                term == null ||
                term.isBlank()) {

            return false;
        }

        String normalizedText =
                text.toLowerCase(Locale.ROOT);

        String normalizedTerm =
                term.toLowerCase(Locale.ROOT);

        /*
         * Multi-word and special technical terms
         * such as C++, C#, Node.js, CI/CD.
         */
        if (normalizedTerm.contains(" ")
                || normalizedTerm.contains(".")
                || normalizedTerm.contains("+")
                || normalizedTerm.contains("#")
                || normalizedTerm.contains("/")
                || normalizedTerm.contains("-")) {

            return normalizedText.contains(
                    normalizedTerm
            );
        }

        return Pattern.compile(
                    "\\b"
                    + Pattern.quote(normalizedTerm)
                    + "\\b",
                    Pattern.CASE_INSENSITIVE
                )
                .matcher(normalizedText)
                .find();
    }

    private boolean containsAny(
            String text,
            String... values) {

        return Arrays.stream(values)
                .anyMatch(text::contains);
    }

    private String normalize(String text) {

        if (text == null) {
            return "";
        }

        return text
                .toLowerCase(Locale.ROOT)
                .replaceAll("\\s+", " ")
                .trim();
    }

    private int clamp(
            int value,
            int minimum,
            int maximum) {

        return Math.max(
                minimum,
                Math.min(value, maximum)
        );
    }
}