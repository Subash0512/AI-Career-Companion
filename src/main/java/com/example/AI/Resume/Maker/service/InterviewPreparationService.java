package com.example.AI.Resume.Maker.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.AI.Resume.Maker.dto.InterviewPreparationResponse;
import com.example.AI.Resume.Maker.dto.ResumeProfile;
import com.example.AI.Resume.Maker.entity.InterviewPreparation;
import com.example.AI.Resume.Maker.exception.AiServiceException;
import com.example.AI.Resume.Maker.repository.InterviewPreparationRepository;

@Service
public class InterviewPreparationService {

    private final ResumeProcessorService resumeProcessorService;
    private final GeminiService geminiService;
    private final InterviewPreparationRepository repository;
    private final ObjectMapper objectMapper;

    public InterviewPreparationService(
            ResumeProcessorService resumeProcessorService,
            GeminiService geminiService,
            InterviewPreparationRepository repository,
            ObjectMapper objectMapper) {

        this.resumeProcessorService = resumeProcessorService;
        this.geminiService = geminiService;
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    public InterviewPreparationResponse prepareInterview(
            MultipartFile file,
            String targetRole) throws Exception {

        String resumeText =
                resumeProcessorService.extractText(file);

        ResumeProfile profile =
                resumeProcessorService.processText(resumeText);

        String prompt = """
                You are an expert technical interviewer.

                Generate interview preparation questions for the target role.

                Target Role:
                %s

                Candidate Resume:
                %s

                Candidate Skills:
                %s

                Return JSON with exactly these fields:

                {
                  "targetRole": "...",
                  "technicalQuestions": [
                    {
                      "question": "...",
                      "category": "...",
                      "difficulty": "...",
                      "expectedAnswer": "..."
                    }
                  ],
                  "projectQuestions": [
                    {
                      "question": "...",
                      "category": "...",
                      "difficulty": "...",
                      "expectedAnswer": "..."
                    }
                  ],
                  "hrQuestions": [
                    {
                      "question": "...",
                      "category": "...",
                      "difficulty": "...",
                      "expectedAnswer": "..."
                    }
                  ]
                }

                Generate useful questions based on the actual resume.
                Do not invent projects or technologies.
                Return only JSON.
                """.formatted(
                        targetRole,
                        resumeText,
                        profile.getSkills()
                );

        InterviewPreparationResponse response;

        try {

            response = geminiService.generateJson(
                    prompt,
                    InterviewPreparationResponse.class
            );

        } catch (Exception e) {

            if (e instanceof AiServiceException) {
                throw e;
            }

            throw new AiServiceException(
                    "Failed to generate interview preparation.",
                    e
            );
        }

        savePreparation(
                file,
                response,
                targetRole
        );

        return response;
    }


    private void savePreparation(
            MultipartFile file,
            InterviewPreparationResponse response,
            String targetRole) throws Exception {

        InterviewPreparation preparation =
                new InterviewPreparation();

        preparation.setFileName(
                file.getOriginalFilename()
        );

        preparation.setTargetRole(targetRole);

        preparation.setGeneratedAt(
                LocalDateTime.now()
        );

        preparation.setTechnicalQuestions(
                objectMapper.writeValueAsString(
                        response.getTechnicalQuestions()
                )
        );

        preparation.setProjectQuestions(
                objectMapper.writeValueAsString(
                        response.getProjectQuestions()
                )
        );

        preparation.setHrQuestions(
                objectMapper.writeValueAsString(
                        response.getHrQuestions()
                )
        );

        repository.save(preparation);
    }


    public List<InterviewPreparation>
    getAllInterviewPreparations() {

        return repository.findAll();
    }
}