package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.entity.FacultyHistory;

public interface FacultyHistoryRepository extends JpaRepository<FacultyHistory, Integer> {}