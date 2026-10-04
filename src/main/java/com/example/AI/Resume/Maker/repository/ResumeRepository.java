package com.example.AI.Resume.Maker.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AI.Resume.Maker.entity.Resume;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

}