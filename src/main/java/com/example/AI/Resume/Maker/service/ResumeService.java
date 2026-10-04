package com.example.AI.Resume.Maker.service;

import org.springframework.stereotype.Service;
import java.util.List;
import com.example.AI.Resume.Maker.entity.Resume;
import com.example.AI.Resume.Maker.repository.ResumeRepository;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    public Resume createResume(Resume resume) {
        return resumeRepository.save(resume);
    }
    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }
}