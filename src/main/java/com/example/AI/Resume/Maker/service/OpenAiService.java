package com.example.AI.Resume.Maker.service;

import org.springframework.stereotype.Service;

import com.example.AI.Resume.Maker.dto.AiResumeFeedbackResponse;

@Service
public class OpenAiService {

    private final GeminiService geminiService;

    public OpenAiService(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    public AiResumeFeedbackResponse analyzeResume(
            String resumeText) {

        if (resumeText == null || resumeText.isBlank()) {
            throw new IllegalArgumentException(
                    "Resume text cannot be empty."
            );
        }

        String prompt = """
                You are an expert professional resume reviewer.

                Analyze the resume below.

                Return JSON with exactly these fields:

                {
                  "strengths": ["..."],
                  "weaknesses": ["..."],
                  "suggestions": ["..."]
                }

                Requirements:

                - Give specific feedback based only on the resume.
                - Do not invent experience, education, skills, projects,
                  certifications or achievements.
                - Strengths should identify genuine strengths.
                - Weaknesses should identify realistic weaknesses.
                - Suggestions should be practical and actionable.
                - Return only JSON.

                RESUME:
                %s
                """.formatted(resumeText);

        return geminiService.generateJson(
                prompt,
                AiResumeFeedbackResponse.class
        );
    }
}