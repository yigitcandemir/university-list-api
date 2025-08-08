package com.example.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.entity.Faculty;

public interface  FacultyRepository extends JpaRepository<Faculty, Integer>{
    List<Faculty> findByDeletedFalse();
}
