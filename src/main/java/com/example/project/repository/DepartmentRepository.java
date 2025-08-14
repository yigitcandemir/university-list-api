package com.example.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.entity.Department;

public interface  DepartmentRepository extends JpaRepository<Department, Integer>{
    List<Department> findByDeletedFalse();
    List<Department> findByFaculty_Campus_University_IdAndDeletedFalseOrderByNameAsc(Integer universityId);
     List<Department> findByFaculty_IdAndDeletedFalseOrderByNameAsc(Integer facultyId);
}
