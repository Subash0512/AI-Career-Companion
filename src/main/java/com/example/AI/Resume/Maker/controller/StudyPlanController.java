package com.example.AI.Resume.Maker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.AI.Resume.Maker.dto.StudyPlanResponse;
import com.example.AI.Resume.Maker.entity.StudyPlan;
import com.example.AI.Resume.Maker.service.StudyPlanService;

@RestController
public class StudyPlanController {

    private final StudyPlanService studyPlanService;

    public StudyPlanController(
            StudyPlanService studyPlanService) {

        this.studyPlanService = studyPlanService;
    }

    // Generate study plan
    @PostMapping("/api/study-plan/generate")
    public StudyPlanResponse generateStudyPlan(
            @RequestParam("file") MultipartFile file,
            @RequestParam("targetRole") String targetRole)
            throws Exception {

        validateText(targetRole, "Target role");

        return studyPlanService.generateStudyPlan(
                file,
                targetRole
        );
    }

    // Get study plan history
    @GetMapping("/api/study-plans")
    public List<StudyPlan> getAllStudyPlans() {

        return studyPlanService.getAllStudyPlans();
    }

    private void validateText(
            String value,
            String fieldName) {

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    fieldName + " is required"
            );
        }
    }
}