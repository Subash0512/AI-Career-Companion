package com.example.AI.Resume.Maker.service;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;

import com.example.AI.Resume.Maker.exception.AiServiceException;

@Service
public class GeminiService {

    private static final String MODEL = "gemini-3.1-flash-lite";

    private final Client client;
    private final ObjectMapper objectMapper;

    public GeminiService(
            Client client,
            ObjectMapper objectMapper) {

        this.client = client;
        this.objectMapper = objectMapper;
    }

    public <T> T generateJson(
            String prompt,
            Class<T> responseType) {

        if (prompt == null || prompt.isBlank()) {
            throw new AiServiceException(
                    "Gemini prompt cannot be empty."
            );
        }

        try {

            GenerateContentConfig config =
                    GenerateContentConfig.builder()
                            .responseMimeType("application/json")
                            .candidateCount(1)
                            .build();

            GenerateContentResponse response =
                    client.models.generateContent(
                            MODEL,
                            prompt,
                            config
                    );

            String responseText = response.text();

            if (responseText == null || responseText.isBlank()) {

                throw new AiServiceException(
                        "Gemini returned an empty response."
                );
            }

            String json = cleanJson(responseText);

            return objectMapper.readValue(
                    json,
                    responseType
            );

        } catch (AiServiceException e) {

            throw e;

        } catch (Exception e) {

            throw new AiServiceException(
                    "Gemini request failed: " + e.getMessage(),
                    e
            );
        }
    }


    private String cleanJson(String text) {

        String cleaned = text.trim();

        if (cleaned.startsWith("```json")) {
            cleaned = cleaned.substring(7).trim();
        }

        if (cleaned.startsWith("```")) {
            cleaned = cleaned.substring(3).trim();
        }

        if (cleaned.endsWith("```")) {
            cleaned =
                    cleaned.substring(
                            0,
                            cleaned.length() - 3
                    ).trim();
        }

        int objectStart = cleaned.indexOf("{");
        int objectEnd = cleaned.lastIndexOf("}");

        if (objectStart >= 0 && objectEnd > objectStart) {

            return cleaned.substring(
                    objectStart,
                    objectEnd + 1
            );
        }

        throw new AiServiceException(
                "Gemini did not return valid JSON."
        );
    }
}