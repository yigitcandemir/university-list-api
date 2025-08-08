package com.example.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.entity.Department;

public interface  DepartmentRepository extends JpaRepository<Department, Integer>{
    List<Department> findByDeletedFalse();
}
