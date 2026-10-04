package com.example.AI.Resume.Maker.controller;

import java.util.List;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import com.example.AI.Resume.Maker.dto.ResumeBuilderResponse;
import com.example.AI.Resume.Maker.entity.GeneratedResume;
import com.example.AI.Resume.Maker.repository.GeneratedResumeRepository;
import com.example.AI.Resume.Maker.service.ResumeBuilderService;
import com.example.AI.Resume.Maker.service.ResumePdfService;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/resume-builder")
public class ResumeBuilderController {

    private final ResumeBuilderService resumeBuilderService;

    private final ResumePdfService resumePdfService;

    private final GeneratedResumeRepository
            generatedResumeRepository;

    private final ObjectMapper objectMapper;

    public ResumeBuilderController(
            ResumeBuilderService resumeBuilderService,
            ResumePdfService resumePdfService,
            GeneratedResumeRepository generatedResumeRepository,
            ObjectMapper objectMapper) {

        this.resumeBuilderService =
                resumeBuilderService;

        this.resumePdfService =
                resumePdfService;

        this.generatedResumeRepository =
                generatedResumeRepository;

        this.objectMapper =
                objectMapper;
    }

    @PostMapping(
            value = "/generate",
            consumes =
                    MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ResumeBuilderResponse>
            generateResume(

            @RequestParam("file")
            MultipartFile file,

            @RequestParam("targetRole")
            String targetRole,

            @RequestParam(
                    value = "jobDescription",
                    required = false
            )
            String jobDescription)
            throws Exception {

        ResumeBuilderResponse response =
                resumeBuilderService.generateResume(
                        file,
                        targetRole,
                        jobDescription
                );

        return ResponseEntity.ok(
                response
        );
    }

    @GetMapping("/history")
    public ResponseEntity<List<GeneratedResume>>
            getHistory() {

        return ResponseEntity.ok(
                resumeBuilderService.getHistory()
        );
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]>
            downloadPdf(
                    @PathVariable Long id)
            throws Exception {

        GeneratedResume generatedResume =
                generatedResumeRepository
                        .findById(id)
                        .orElseThrow(
                                () -> new IllegalArgumentException(
                                        "Generated resume not found."
                                )
                        );

        GeneratedResumePdfData data =
                new GeneratedResumePdfData(
                        generatedResume
                );

        byte[] pdf =
                resumePdfService.generatePdf(
                        objectMapper.readValue(
                                data.resumeContent(),
                                com.example.AI.Resume.Maker.dto.GeneratedResumeContent.class
                        )
                );

        String downloadName =
                buildDownloadFileName(
                        generatedResume.getTargetRole()
                );

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_PDF
        );

        headers.setContentDisposition(
                ContentDisposition
                        .attachment()
                        .filename(downloadName)
                        .build()
        );

        headers.setContentLength(
                pdf.length
        );

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(pdf);
    }

    private String buildDownloadFileName(
            String targetRole) {

        String role =
                targetRole == null
                        ? "resume"
                        : targetRole
                                .trim()
                                .replaceAll(
                                        "[^a-zA-Z0-9]+",
                                        "-"
                                )
                                .replaceAll(
                                        "^-|-$",
                                        ""
                                );

        if (role.isBlank()) {
            role = "resume";
        }

        return role
                + "-optimized-resume.pdf";
    }

    private record GeneratedResumePdfData(
            String resumeContent) {

        GeneratedResumePdfData(
                GeneratedResume resume) {

            this(
                    resume.getResumeContent()
            );
        }
    }
}