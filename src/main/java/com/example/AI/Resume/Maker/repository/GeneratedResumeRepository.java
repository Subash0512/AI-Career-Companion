package com.example.AI.Resume.Maker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AI.Resume.Maker.entity.GeneratedResume;

public interface GeneratedResumeRepository
        extends JpaRepository<GeneratedResume, Long> {
}