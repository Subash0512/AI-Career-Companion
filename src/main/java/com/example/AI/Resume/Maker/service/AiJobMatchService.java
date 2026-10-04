package com.example.AI.Resume.Maker.service;

import org.springframework.stereotype.Service;

import com.example.AI.Resume.Maker.dto.AiJobMatchFeedbackResponse;

@Service
public class AiJobMatchService {

    private final GeminiService geminiService;

    public AiJobMatchService(
            GeminiService geminiService) {

        this.geminiService =
                geminiService;
    }

    public AiJobMatchFeedbackResponse analyzeJobMatch(
            String resumeText,
            String jobDescription,
            String matchedSkills,
            String missingSkills) {

        if (resumeText == null ||
                resumeText.isBlank()) {

            throw new IllegalArgumentException(
                    "Resume text cannot be empty."
            );
        }

        if (jobDescription == null ||
                jobDescription.isBlank()) {

            throw new IllegalArgumentException(
                    "Job description cannot be empty."
            );
        }

        String prompt = """
                You are an expert technical recruiter
                and resume reviewer.

                Analyze the compatibility between the
                candidate resume and the job description.

                IMPORTANT RULES:
                - Use only information provided.
                - Never invent experience.
                - Never invent skills.
                - Never claim that the candidate has a
                  missing skill.
                - Explain the match clearly.
                - Give practical recommendations.
                - Give realistic resume improvements.
                - Return ONLY valid JSON.

                Return exactly:

                {
                  "summary": "...",
                  "recommendations": [
                    "...",
                    "..."
                  ],
                  "improvements": [
                    "...",
                    "..."
                  ]
                }

                RESUME:
                %s

                JOB DESCRIPTION:
                %s

                MATCHED SKILLS:
                %s

                MISSING SKILLS:
                %s
                """
                .formatted(
                        resumeText,
                        jobDescription,
                        matchedSkills,
                        missingSkills
                );

        return geminiService.generateJson(
                prompt,
                AiJobMatchFeedbackResponse.class
        );
    }
}