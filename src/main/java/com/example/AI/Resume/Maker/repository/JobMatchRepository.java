package com.example.AI.Resume.Maker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AI.Resume.Maker.entity.JobMatch;

public interface JobMatchRepository
        extends JpaRepository<JobMatch, Long> {
}