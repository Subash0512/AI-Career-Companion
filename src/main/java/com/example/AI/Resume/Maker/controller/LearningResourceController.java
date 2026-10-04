package com.example.AI.Resume.Maker.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.AI.Resume.Maker.dto.LearningResource;
import com.example.AI.Resume.Maker.service.LearningResourceService;

@RestController
@RequestMapping("/api/learning-resources")
public class LearningResourceController {

    private final LearningResourceService
            learningResourceService;

    public LearningResourceController(
            LearningResourceService learningResourceService) {

        this.learningResourceService =
                learningResourceService;
    }

    /**
     * Example:
     *
     * /api/learning-resources?skills=Docker,AWS,Microservices
     */
    @GetMapping
    public ResponseEntity<List<LearningResource>>
            getResources(

            @RequestParam("skills")
            String skills) {

        List<String> requestedSkills =
                Arrays.stream(
                                skills.split(",")
                        )
                        .map(String::trim)
                        .filter(
                                skill ->
                                        !skill.isBlank()
                        )
                        .toList();

        return ResponseEntity.ok(
                learningResourceService
                        .getResources(
                                requestedSkills
                        )
        );
    }

    /**
     * Returns the full supported platform catalog.
     */
    @GetMapping("/all")
    public ResponseEntity<List<LearningResource>>
            getAllResources() {

        return ResponseEntity.ok(
                learningResourceService
                        .getAllResources()
        );
    }
}