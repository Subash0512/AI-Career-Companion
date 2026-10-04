package com.example.AI.Resume.Maker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.AI.Resume.Maker.dto.CareerGuidanceResponse;
import com.example.AI.Resume.Maker.entity.CareerGuidance;
import com.example.AI.Resume.Maker.service.CareerGuidanceService;

@RestController
public class CareerController {

    private final CareerGuidanceService careerGuidanceService;

    public CareerController(
            CareerGuidanceService careerGuidanceService) {

        this.careerGuidanceService = careerGuidanceService;
    }

    // Generate career guidance
    @PostMapping("/api/career/guidance")
    public CareerGuidanceResponse generateCareerGuidance(
            @RequestParam("file") MultipartFile file,
            @RequestParam("targetRole") String targetRole)
            throws Exception {

        validateText(targetRole, "Target role");

        return careerGuidanceService.generateCareerGuidance(
                file,
                targetRole
        );
    }

    // Get career guidance history
    @GetMapping("/api/career/guidance/history")
    public List<CareerGuidance> getAllCareerGuidance() {

        return careerGuidanceService.getAllCareerGuidance();
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