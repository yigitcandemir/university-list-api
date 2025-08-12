package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.entity.CampusHistory;

public interface CampusHistoryRepository extends JpaRepository<CampusHistory, Integer> {}
