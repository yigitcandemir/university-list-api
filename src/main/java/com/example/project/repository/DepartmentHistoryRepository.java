package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.entity.DepartmentHistory;

public interface DepartmentHistoryRepository extends JpaRepository<DepartmentHistory, Integer> {}