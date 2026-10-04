package com.example.AI.Resume.Maker.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.AI.Resume.Maker.dto.AiJobMatchFeedbackResponse;
import com.example.AI.Resume.Maker.dto.AtsAnalysisResponse;
import com.example.AI.Resume.Maker.dto.JobMatchResponse;
import com.example.AI.Resume.Maker.entity.JobMatch;
import com.example.AI.Resume.Maker.repository.JobMatchRepository;

@Service
public class JobMatchService {

    private final ResumeProcessorService resumeProcessorService;

    private final AtsAnalysisService atsAnalysisService;

    private final AiJobMatchService aiJobMatchService;

    private final JobMatchRepository jobMatchRepository;

    public JobMatchService(
            ResumeProcessorService resumeProcessorService,
            AtsAnalysisService atsAnalysisService,
            AiJobMatchService aiJobMatchService,
            JobMatchRepository jobMatchRepository) {

        this.resumeProcessorService =
                resumeProcessorService;

        this.atsAnalysisService =
                atsAnalysisService;

        this.aiJobMatchService =
                aiJobMatchService;

        this.jobMatchRepository =
                jobMatchRepository;
    }

    /**
     * Main method used by the Job Matcher controller.
     */
    public JobMatchResponse matchResumeWithJob(
            MultipartFile file,
            String jobTitle,
            String jobDescription)
            throws Exception {

        validateInput(
                file,
                jobTitle,
                jobDescription
        );

        String resumeText =
                resumeProcessorService.extractText(file);

        AtsAnalysisResponse atsResult =
                atsAnalysisService.analyze(
                        resumeText,
                        jobTitle,
                        jobDescription
                );

        List<String> matchedSkills =
                combineUnique(
                        atsResult.getMatchedRequiredSkills(),
                        atsResult.getMatchedPreferredSkills()
                );

        List<String> missingSkills =
                combineUnique(
                        atsResult.getMissingRequiredSkills(),
                        atsResult.getMissingPreferredSkills()
                );

        String matchedSkillsText =
                joinSkills(matchedSkills);

        String missingSkillsText =
                joinSkills(missingSkills);

        AiJobMatchFeedbackResponse aiResult =
                aiJobMatchService.analyzeJobMatch(
                        resumeText,
                        jobDescription,
                        matchedSkillsText,
                        missingSkillsText
                );

        JobMatch entity =
                new JobMatch();

        entity.setFileName(
                safeFileName(
                        file.getOriginalFilename()
                )
        );

        entity.setJobTitle(
                jobTitle.trim()
        );

        entity.setMatchScore(
                atsResult.getScore()
        );

        entity.setRequiredSkillScore(
                atsResult.getRequiredSkillScore()
        );

        entity.setPreferredSkillScore(
                atsResult.getPreferredSkillScore()
        );

        entity.setKeywordScore(
                atsResult.getKeywordScore()
        );

        entity.setRelevanceScore(
                atsResult.getRelevanceScore()
        );

        entity.setStructureScore(
                atsResult.getStructureScore()
        );

        entity.setContentScore(
                atsResult.getContentScore()
        );

        entity.setMatchedSkills(
                new ArrayList<>(matchedSkills)
        );

        entity.setMissingSkills(
                new ArrayList<>(missingSkills)
        );

        entity.setMatchingKeywords(
                new ArrayList<>(
                        atsResult.getMatchedKeywords()
                )
        );

        entity.setMissingKeywords(
                new ArrayList<>(
                        atsResult.getMissingKeywords()
                )
        );

        entity.setMatchedRequiredSkills(
                new ArrayList<>(
                        atsResult.getMatchedRequiredSkills()
                )
        );

        entity.setMissingRequiredSkills(
                new ArrayList<>(
                        atsResult.getMissingRequiredSkills()
                )
        );

        entity.setMatchedPreferredSkills(
                new ArrayList<>(
                        atsResult.getMatchedPreferredSkills()
                )
        );

        entity.setMissingPreferredSkills(
                new ArrayList<>(
                        atsResult.getMissingPreferredSkills()
                )
        );

        entity.setAiSummary(
                aiResult.getSummary()
        );

        entity.setAiRecommendations(
                safeList(
                        aiResult.getRecommendations()
                )
        );

        entity.setAiImprovements(
                safeList(
                        aiResult.getImprovements()
                )
        );

        entity.setMatchedAt(
                LocalDateTime.now()
        );

        JobMatch saved =
                jobMatchRepository.save(entity);

        return toResponse(saved);
    }

    /**
     * Compatibility alias in case your old controller
     * calls matchResume().
     */
    public JobMatchResponse matchResume(
            MultipartFile file,
            String jobTitle,
            String jobDescription)
            throws Exception {

        return matchResumeWithJob(
                file,
                jobTitle,
                jobDescription
        );
    }

    public List<JobMatch> getAllMatches() {

        return jobMatchRepository.findAll();
    }

    private JobMatchResponse toResponse(
            JobMatch entity) {

        JobMatchResponse response =
                new JobMatchResponse();

        response.setId(
                entity.getId()
        );

        response.setFileName(
                entity.getFileName()
        );

        response.setJobTitle(
                entity.getJobTitle()
        );

        response.setMatchScore(
                entity.getMatchScore()
        );

        response.setRequiredSkillScore(
                entity.getRequiredSkillScore()
        );

        response.setPreferredSkillScore(
                entity.getPreferredSkillScore()
        );

        response.setKeywordScore(
                entity.getKeywordScore()
        );

        response.setRelevanceScore(
                entity.getRelevanceScore()
        );

        response.setStructureScore(
                entity.getStructureScore()
        );

        response.setContentScore(
                entity.getContentScore()
        );

        response.setMatchedSkills(
                safeList(
                        entity.getMatchedSkills()
                )
        );

        response.setMissingSkills(
                safeList(
                        entity.getMissingSkills()
                )
        );

        response.setMatchingKeywords(
                safeList(
                        entity.getMatchingKeywords()
                )
        );

        response.setMissingKeywords(
                safeList(
                        entity.getMissingKeywords()
                )
        );

        response.setMatchedRequiredSkills(
                safeList(
                        entity.getMatchedRequiredSkills()
                )
        );

        response.setMissingRequiredSkills(
                safeList(
                        entity.getMissingRequiredSkills()
                )
        );

        response.setMatchedPreferredSkills(
                safeList(
                        entity.getMatchedPreferredSkills()
                )
        );

        response.setMissingPreferredSkills(
                safeList(
                        entity.getMissingPreferredSkills()
                )
        );

        response.setAiSummary(
                entity.getAiSummary()
        );

        response.setAiRecommendations(
                safeList(
                        entity.getAiRecommendations()
                )
        );

        response.setAiImprovements(
                safeList(
                        entity.getAiImprovements()
                )
        );

        return response;
    }

    private void validateInput(
            MultipartFile file,
            String jobTitle,
            String jobDescription) {

        if (file == null ||
                file.isEmpty()) {

            throw new IllegalArgumentException(
                    "Resume PDF is required."
            );
        }

        String contentType =
                file.getContentType();

        String originalName =
                file.getOriginalFilename();

        boolean isPdf =
                "application/pdf".equalsIgnoreCase(
                        contentType
                )
                ||
                (
                    originalName != null
                    &&
                    originalName
                            .toLowerCase()
                            .endsWith(".pdf")
                );

        if (!isPdf) {

            throw new IllegalArgumentException(
                    "Only PDF resume files are supported."
            );
        }

        if (jobTitle == null ||
                jobTitle.isBlank()) {

            throw new IllegalArgumentException(
                    "Job title cannot be empty."
            );
        }

        if (jobDescription == null ||
                jobDescription.isBlank()) {

            throw new IllegalArgumentException(
                    "Job description cannot be empty."
            );
        }
    }

    private String safeFileName(
            String fileName) {

        if (fileName == null ||
                fileName.isBlank()) {

            return "resume.pdf";
        }

        return fileName;
    }

    private List<String> combineUnique(
            List<String> first,
            List<String> second) {

        Set<String> values =
                new LinkedHashSet<>();

        if (first != null) {
            values.addAll(first);
        }

        if (second != null) {
            values.addAll(second);
        }

        return new ArrayList<>(values);
    }

    private String joinSkills(
            List<String> skills) {

        if (skills == null ||
                skills.isEmpty()) {

            return "None";
        }

        return skills.stream()
                .filter(
                        skill ->
                                skill != null
                                && !skill.isBlank()
                )
                .collect(
                        Collectors.joining(", ")
                );
    }

    private <T> List<T> safeList(
            List<T> list) {

        if (list == null) {
            return new ArrayList<>();
        }

        return new ArrayList<>(list);
    }
}