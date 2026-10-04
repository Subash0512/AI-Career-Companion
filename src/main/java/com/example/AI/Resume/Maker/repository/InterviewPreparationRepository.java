package com.example.AI.Resume.Maker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AI.Resume.Maker.entity.InterviewPreparation;

public interface InterviewPreparationRepository
        extends JpaRepository<InterviewPreparation, Long> {

}