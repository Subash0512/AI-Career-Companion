package com.example.AI.Resume.Maker.controller;

import java.util.List;
import com.example.AI.Resume.Maker.entity.InterviewPreparation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.AI.Resume.Maker.dto.InterviewPreparationResponse;
import com.example.AI.Resume.Maker.service.InterviewPreparationService;

@RestController
public class InterviewController {

    private final InterviewPreparationService interviewPreparationService;

    public InterviewController(
            InterviewPreparationService interviewPreparationService) {

        this.interviewPreparationService =
                interviewPreparationService;
    }

    // Generate interview preparation
    @PostMapping("/api/interview/prepare")
    public InterviewPreparationResponse prepareInterview(
            @RequestParam("file") MultipartFile file,
            @RequestParam("targetRole") String targetRole)
            throws Exception {

        validateText(targetRole, "Target role");

        return interviewPreparationService.prepareInterview(
                file,
                targetRole
        );
    }

    // Get interview preparation history
    @GetMapping("/api/interview/preparations")
    public List<InterviewPreparation> getAllInterviewPreparations() {

        return interviewPreparationService
                .getAllInterviewPreparations();
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