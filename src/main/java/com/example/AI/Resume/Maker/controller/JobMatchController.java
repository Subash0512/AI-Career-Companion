package com.example.AI.Resume.Maker.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.AI.Resume.Maker.dto.JobMatchResponse;
import com.example.AI.Resume.Maker.entity.JobMatch;
import com.example.AI.Resume.Maker.service.JobMatchService;

@RestController
@RequestMapping("/api")
public class JobMatchController {

    private final JobMatchService jobMatchService;

    public JobMatchController(
            JobMatchService jobMatchService) {

        this.jobMatchService =
                jobMatchService;
    }

    @PostMapping(
            value = "/resumes/match",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<JobMatchResponse> matchResume(
            @RequestParam("file") MultipartFile file,
            @RequestParam("jobTitle") String jobTitle,
            @RequestParam("jobDescription") String jobDescription)
            throws Exception {

        JobMatchResponse response =
                jobMatchService.matchResumeWithJob(
                        file,
                        jobTitle,
                        jobDescription
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/job-matches")
    public ResponseEntity<List<JobMatch>> getAllMatches() {

        return ResponseEntity.ok(
                jobMatchService.getAllMatches()
        );
    }
}