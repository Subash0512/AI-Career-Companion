package com.example.AI.Resume.Maker.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.AI.Resume.Maker.dto.CareerGuidanceResponse;
import com.example.AI.Resume.Maker.dto.ResumeProfile;
import com.example.AI.Resume.Maker.entity.CareerGuidance;
import com.example.AI.Resume.Maker.repository.CareerGuidanceRepository;

@Service
public class CareerGuidanceService {

    private final ResumeProcessorService resumeProcessorService;
    private final GeminiService geminiService;
    private final CareerGuidanceRepository repository;
    private final ObjectMapper objectMapper;

    public CareerGuidanceService(
            ResumeProcessorService resumeProcessorService,
            GeminiService geminiService,
            CareerGuidanceRepository repository,
            ObjectMapper objectMapper) {

        this.resumeProcessorService = resumeProcessorService;
        this.geminiService = geminiService;
        this.repository = repository;
        this.objectMapper = objectMapper;
    }


    public CareerGuidanceResponse generateCareerGuidance(
            MultipartFile file,
            String targetRole) throws Exception {

        String resumeText =
                resumeProcessorService.extractText(file);

        ResumeProfile profile =
                resumeProcessorService.processText(resumeText);

        String prompt = """
                You are an expert career advisor.

                Analyze this candidate and provide practical career guidance.

                Target Role:
                %s

                Resume:
                %s

                Detected Skills:
                %s

                Return JSON with exactly these fields:

                {
                  "profileSummary": "...",
                  "recommendedRoles": ["..."],
                  "skillGaps": ["..."],
                  "skillsToLearn": ["..."],
                  "recommendedProjects": ["..."],
                  "actionPlan": ["..."]
                }

                Rules:

                - Base recommendations on the actual candidate information.
                - Do not invent qualifications.
                - Keep recommendations realistic.
                - Projects should be relevant to the target role.
                - Action plan should be practical and sequential.
                - Return only JSON.
                """.formatted(
                        targetRole,
                        resumeText,
                        profile.getSkills()
                );

        CareerGuidanceResponse response =
                geminiService.generateJson(
                        prompt,
                        CareerGuidanceResponse.class
                );

        saveCareerGuidance(
                file,
                targetRole,
                response
        );

        return response;
    }


    private void saveCareerGuidance(
            MultipartFile file,
            String targetRole,
            CareerGuidanceResponse response)
            throws Exception {

        CareerGuidance careerGuidance =
                new CareerGuidance();

        careerGuidance.setFileName(
                file.getOriginalFilename()
        );

        careerGuidance.setTargetRole(
                targetRole
        );

        careerGuidance.setGeneratedAt(
                LocalDateTime.now()
        );

        careerGuidance.setProfileSummary(
                response.getProfileSummary()
        );

        careerGuidance.setRecommendedRoles(
                objectMapper.writeValueAsString(
                        response.getRecommendedRoles()
                )
        );

        careerGuidance.setSkillGaps(
                objectMapper.writeValueAsString(
                        response.getSkillGaps()
                )
        );

        careerGuidance.setSkillsToLearn(
                objectMapper.writeValueAsString(
                        response.getSkillsToLearn()
                )
        );

        careerGuidance.setRecommendedProjects(
                objectMapper.writeValueAsString(
                        response.getRecommendedProjects()
                )
        );

        careerGuidance.setActionPlan(
                objectMapper.writeValueAsString(
                        response.getActionPlan()
                )
        );

        repository.save(careerGuidance);
    }


    public List<CareerGuidance>
    getAllCareerGuidance() {

        return repository.findAll();
    }
}