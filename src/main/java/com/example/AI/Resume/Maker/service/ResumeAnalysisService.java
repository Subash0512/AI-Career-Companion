package com.example.AI.Resume.Maker.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.AI.Resume.Maker.dto.ResumeAnalysisResponse;
import com.example.AI.Resume.Maker.entity.ResumeAnalysis;
import com.example.AI.Resume.Maker.repository.ResumeAnalysisRepository;

@Service
public class ResumeAnalysisService {

    private final ResumeProcessorService resumeProcessorService;
    private final RuleBasedAnalyzer ruleBasedAnalyzer;
    private final OpenAiService openAiService;
    private final ResumeAnalysisRepository resumeAnalysisRepository;

    public ResumeAnalysisService(
            ResumeProcessorService resumeProcessorService,
            RuleBasedAnalyzer ruleBasedAnalyzer,
            OpenAiService openAiService,
            ResumeAnalysisRepository resumeAnalysisRepository) {

        this.resumeProcessorService = resumeProcessorService;
        this.ruleBasedAnalyzer = ruleBasedAnalyzer;
        this.openAiService = openAiService;
        this.resumeAnalysisRepository = resumeAnalysisRepository;
    }

    public ResumeAnalysisResponse analyzeResume(
            MultipartFile file) throws Exception {

        // 1. Extract actual text from uploaded resume
        String resumeText =
                resumeProcessorService.extractText(file);

        // 2. Analyze using deterministic Java rules
        ResumeAnalysisResponse ruleResult =
                ruleBasedAnalyzer.analyze(resumeText);

        // 3. Analyze using Gemini through OpenAiService
        var aiResult =
                openAiService.analyzeResume(resumeText);

        // 4. Combine rule-based + AI results
        ResumeAnalysisResponse finalResponse =
                new ResumeAnalysisResponse(
                        ruleResult.getScore(),
                        ruleResult.getSkills(),
                        ruleResult.getMissingSkills(),
                        aiResult.getStrengths(),
                        aiResult.getWeaknesses(),
                        aiResult.getSuggestions()
                );

        // 5. Save analysis in MySQL
        saveAnalysis(file, finalResponse);

        return finalResponse;
    }

    public String extractResumeText(
            MultipartFile file) throws Exception {

        return resumeProcessorService.extractText(file);
    }

    private void saveAnalysis(
            MultipartFile file,
            ResumeAnalysisResponse response) {

        ResumeAnalysis analysis =
                new ResumeAnalysis();

        analysis.setFileName(
                file.getOriginalFilename()
        );

        analysis.setScore(
                response.getScore()
        );

        analysis.setSkills(
                response.getSkills()
        );

        analysis.setMissingSkills(
                response.getMissingSkills()
        );

        analysis.setStrengths(
                response.getStrengths()
        );

        analysis.setWeaknesses(
                response.getWeaknesses()
        );

        analysis.setSuggestions(
                response.getSuggestions()
        );

        analysis.setAnalyzedAt(
                LocalDateTime.now()
        );

        resumeAnalysisRepository.save(analysis);
    }

    public List<ResumeAnalysis> getAllAnalyses() {

        return resumeAnalysisRepository.findAll();
    }
}