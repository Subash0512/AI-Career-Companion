package com.example.AI.Resume.Maker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.AI.Resume.Maker.dto.ResumeAnalysisResponse;
import com.example.AI.Resume.Maker.entity.Resume;
import com.example.AI.Resume.Maker.entity.ResumeAnalysis;
import com.example.AI.Resume.Maker.service.ResumeAnalysisService;
import com.example.AI.Resume.Maker.service.ResumeService;

import jakarta.validation.Valid;

@RestController
public class ResumeController {

    private final ResumeService resumeService;
    private final ResumeAnalysisService resumeAnalysisService;

    public ResumeController(
            ResumeService resumeService,
            ResumeAnalysisService resumeAnalysisService) {

        this.resumeService = resumeService;
        this.resumeAnalysisService = resumeAnalysisService;
    }

    // =========================================================
    // CREATE RESUME
    // =========================================================

    @PostMapping("/api/resumes")
    public Resume createResume(
            @Valid @RequestBody Resume resume) {

        return resumeService.createResume(resume);
    }

    // =========================================================
    // GET ALL RESUMES
    // =========================================================

    @GetMapping("/api/resumes")
    public List<Resume> getAllResumes() {

        return resumeService.getAllResumes();
    }

    // =========================================================
    // RESUME ANALYSIS
    // =========================================================

    @PostMapping("/api/resumes/analyze")
    public ResumeAnalysisResponse analyzeResume(
            @RequestParam("file") MultipartFile file)
            throws Exception {

        return resumeAnalysisService.analyzeResume(file);
    }

    // =========================================================
    // GET ALL ANALYSIS HISTORY
    // =========================================================

    @GetMapping("/api/analyses")
    public List<ResumeAnalysis> getAllAnalyses() {

        return resumeAnalysisService.getAllAnalyses();
    }
}