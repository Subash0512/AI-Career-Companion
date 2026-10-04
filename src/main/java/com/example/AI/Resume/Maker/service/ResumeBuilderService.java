package com.example.AI.Resume.Maker.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.AI.Resume.Maker.dto.AtsAnalysisResponse;
import com.example.AI.Resume.Maker.dto.GeneratedResumeContent;
import com.example.AI.Resume.Maker.dto.ResumeBuilderResponse;
import com.example.AI.Resume.Maker.entity.GeneratedResume;
import com.example.AI.Resume.Maker.repository.GeneratedResumeRepository;

@Service
public class ResumeBuilderService {

    private final ResumeProcessorService resumeProcessorService;

    private final GeminiService geminiService;

    private final AtsAnalysisService atsAnalysisService;

    private final GeneratedResumeRepository
            generatedResumeRepository;

    private final ObjectMapper objectMapper;

    public ResumeBuilderService(
            ResumeProcessorService resumeProcessorService,
            GeminiService geminiService,
            AtsAnalysisService atsAnalysisService,
            GeneratedResumeRepository generatedResumeRepository,
            ObjectMapper objectMapper) {

        this.resumeProcessorService =
                resumeProcessorService;

        this.geminiService =
                geminiService;

        this.atsAnalysisService =
                atsAnalysisService;

        this.generatedResumeRepository =
                generatedResumeRepository;

        this.objectMapper =
                objectMapper;
    }

    public ResumeBuilderResponse generateResume(
            MultipartFile file,
            String targetRole,
            String jobDescription)
            throws Exception {

        validateInput(
                file,
                targetRole
        );

        String resumeText =
                resumeProcessorService.extractText(file);

        String prompt =
                buildPrompt(
                        resumeText,
                        targetRole,
                        jobDescription
                );

        GeneratedResumeContent generatedResume =
                geminiService.generateJson(
                        prompt,
                        GeneratedResumeContent.class
                );

        cleanGeneratedResume(generatedResume);

        AtsAnalysisResponse atsAnalysis =
                null;

        String atsMessage;

        if (jobDescription != null
                && !jobDescription.isBlank()) {

            String optimizedResumeText =
                    objectMapper.writeValueAsString(
                            generatedResume
                    );

            atsAnalysis =
                    atsAnalysisService.analyze(
                            optimizedResumeText,
                            targetRole,
                            jobDescription
                    );

            atsMessage =
                    "ATS analysis was calculated against the provided job description.";

        } else {

            atsMessage =
                    "Job description was not provided. Add a job description for a job-specific ATS score.";
        }

        GeneratedResume entity =
                new GeneratedResume();

        entity.setFileName(
                getFileName(
                        file.getOriginalFilename()
                )
        );

        entity.setTargetRole(
                targetRole.trim()
        );

        entity.setJobDescription(
                jobDescription
        );

        entity.setResumeContent(
                objectMapper.writeValueAsString(
                        generatedResume
                )
        );

        entity.setAtsScore(
                atsAnalysis != null
                        ? atsAnalysis.getScore()
                        : 0
        );

        entity.setGeneratedAt(
                LocalDateTime.now()
        );

        GeneratedResume saved =
                generatedResumeRepository.save(
                        entity
                );

        ResumeBuilderResponse response =
                new ResumeBuilderResponse();

        response.setId(
                saved.getId()
        );

        response.setFileName(
                saved.getFileName()
        );

        response.setTargetRole(
                saved.getTargetRole()
        );

        response.setResume(
                generatedResume
        );

        response.setAtsAnalysis(
                atsAnalysis
        );

        response.setAtsMessage(
                atsMessage
        );

        return response;
    }

    public List<GeneratedResume> getHistory() {

        return generatedResumeRepository.findAll();
    }

    private String buildPrompt(
            String resumeText,
            String targetRole,
            String jobDescription) {

        String jobDescriptionSection;

        if (jobDescription == null
                || jobDescription.isBlank()) {

            jobDescriptionSection =
                    "No job description was provided. "
                    + "Tailor the resume to the target role only.";
        } else {

            jobDescriptionSection =
                    jobDescription;
        }

        return """
                You are an expert professional resume writer.

                Your task is to transform the candidate's existing
                resume into a professional, ATS-friendly resume
                targeted to the specified job role.

                TARGET ROLE:
                %s

                JOB DESCRIPTION:
                %s

                ORIGINAL RESUME:
                %s

                STRICT RULES:

                1. Never invent information.

                2. Do not add skills that are not supported by the
                   original resume.

                3. Do not invent companies, job titles, education,
                   projects, certifications, dates, achievements,
                   metrics, awards, technologies or responsibilities.

                4. You may improve wording, grammar and structure.

                5. You may reorganize existing information to make
                   it more relevant to the target role.

                6. You may make existing project or experience
                   descriptions more professional, but every claim
                   must remain supported by the original resume.

                7. Do not claim that the candidate has a skill simply
                   because the job description requires it.

                8. If the job description contains skills missing
                   from the resume, do NOT add them to the generated
                   resume.

                9. Keep the resume ATS-friendly:
                   - clear section names
                   - simple professional wording
                   - no unnecessary graphics
                   - no invented keywords
                   - concise bullet points

                10. Preserve factual contact information exactly
                    when available.

                RETURN ONLY VALID JSON.

                Return exactly this structure:

                {
                  "fullName": "...",
                  "email": "...",
                  "phone": "...",
                  "location": "...",
                  "linkedin": "...",
                  "github": "...",
                  "portfolio": "...",
                  "professionalSummary": "...",
                  "skills": [
                    "..."
                  ],
                  "experience": [
                    {
                      "jobTitle": "...",
                      "company": "...",
                      "location": "...",
                      "startDate": "...",
                      "endDate": "...",
                      "responsibilities": [
                        "..."
                      ]
                    }
                  ],
                  "projects": [
                    {
                      "name": "...",
                      "description": "...",
                      "technologies": [
                        "..."
                      ],
                      "contributions": [
                        "..."
                      ],
                      "projectUrl": "..."
                    }
                  ],
                  "education": [
                    {
                      "degree": "...",
                      "institution": "...",
                      "location": "...",
                      "startDate": "...",
                      "endDate": "...",
                      "grade": "..."
                    }
                  ],
                  "certifications": [
                    "..."
                  ],
                  "achievements": [
                    "..."
                  ],
                  "languages": [
                    "..."
                  ]
                }
                """.formatted(
                        targetRole,
                        jobDescriptionSection,
                        resumeText
                );
    }

    private void cleanGeneratedResume(
            GeneratedResumeContent resume) {

        if (resume == null) {
            throw new IllegalStateException(
                    "Gemini did not generate a resume."
            );
        }

        if (resume.getSkills() == null) {
            resume.setSkills(
                    new ArrayList<>()
            );
        }

        if (resume.getExperience() == null) {
            resume.setExperience(
                    new ArrayList<>()
            );
        }

        if (resume.getProjects() == null) {
            resume.setProjects(
                    new ArrayList<>()
            );
        }

        if (resume.getEducation() == null) {
            resume.setEducation(
                    new ArrayList<>()
            );
        }

        if (resume.getCertifications() == null) {
            resume.setCertifications(
                    new ArrayList<>()
            );
        }

        if (resume.getAchievements() == null) {
            resume.setAchievements(
                    new ArrayList<>()
            );
        }

        if (resume.getLanguages() == null) {
            resume.setLanguages(
                    new ArrayList<>()
            );
        }
    }

    private void validateInput(
            MultipartFile file,
            String targetRole) {

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

        if (targetRole == null ||
                targetRole.isBlank()) {

            throw new IllegalArgumentException(
                    "Target job role cannot be empty."
            );
        }
    }

    private String getFileName(
            String originalName) {

        if (originalName == null
                || originalName.isBlank()) {

            return "resume.pdf";
        }

        return originalName;
    }
}