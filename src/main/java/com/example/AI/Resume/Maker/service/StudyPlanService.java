package com.example.AI.Resume.Maker.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.example.AI.Resume.Maker.dto.ResumeProfile;
import com.example.AI.Resume.Maker.dto.StudyPlanResponse;
import com.example.AI.Resume.Maker.entity.StudyPlan;
import com.example.AI.Resume.Maker.repository.StudyPlanRepository;

@Service
public class StudyPlanService {

    private final ResumeProcessorService resumeProcessorService;
    private final GeminiService geminiService;
    private final StudyPlanRepository repository;
    private final ObjectMapper objectMapper;

    public StudyPlanService(
            ResumeProcessorService resumeProcessorService,
            GeminiService geminiService,
            StudyPlanRepository repository,
            ObjectMapper objectMapper) {

        this.resumeProcessorService = resumeProcessorService;
        this.geminiService = geminiService;
        this.repository = repository;
        this.objectMapper = objectMapper;
    }


    public StudyPlanResponse generateStudyPlan(
            MultipartFile file,
            String targetRole) throws Exception {

        String resumeText =
                resumeProcessorService.extractText(file);

        ResumeProfile profile =
                resumeProcessorService.processText(resumeText);

        String prompt = """
                You are an expert technical learning advisor.

                Create a personalized study plan for this candidate.

                Target Role:
                %s

                Resume:
                %s

                Current Skills:
                %s

                Return JSON with exactly these fields:

                {
                  "targetRole": "...",
                  "currentLevel": "...",
                  "overallGoal": "...",
                  "estimatedWeeks": 0,
                  "topics": [
                    {
                      "topic": "...",
                      "priority": "...",
                      "reason": "...",
                      "estimatedTime": "...",
                      "practiceTask": "..."
                    }
                  ],
                  "weeklyPlan": ["..."],
                  "recommendedProjects": ["..."]
                }

                Rules:

                - Base the plan on the actual candidate profile.
                - Focus on skills required for the target role.
                - Do not claim the candidate knows skills not present in the resume.
                - Make the learning plan practical.
                - Include projects that reinforce the learning.
                - Return only JSON.
                """.formatted(
                        targetRole,
                        resumeText,
                        profile.getSkills()
                );

        StudyPlanResponse response =
                geminiService.generateJson(
                        prompt,
                        StudyPlanResponse.class
                );

        saveStudyPlan(
                file,
                response
        );

        return response;
    }


    private void saveStudyPlan(
            MultipartFile file,
            StudyPlanResponse response)
            throws Exception {

        StudyPlan studyPlan =
                new StudyPlan();

        studyPlan.setFileName(
                file.getOriginalFilename()
        );

        studyPlan.setTargetRole(
                response.getTargetRole()
        );

        studyPlan.setCurrentLevel(
                response.getCurrentLevel()
        );

        studyPlan.setEstimatedWeeks(
                response.getEstimatedWeeks()
        );

        studyPlan.setGeneratedAt(
                LocalDateTime.now()
        );

        studyPlan.setOverallGoal(
                response.getOverallGoal()
        );

        studyPlan.setTopics(
                objectMapper.writeValueAsString(
                        response.getTopics()
                )
        );

        studyPlan.setWeeklyPlan(
                objectMapper.writeValueAsString(
                        response.getWeeklyPlan()
                )
        );

        studyPlan.setRecommendedProjects(
                objectMapper.writeValueAsString(
                        response.getRecommendedProjects()
                )
        );

        repository.save(studyPlan);
    }


    public List<StudyPlan> getAllStudyPlans() {

        return repository.findAll();
    }
}