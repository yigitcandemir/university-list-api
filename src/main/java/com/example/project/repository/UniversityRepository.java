package com.example.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.entity.University;


@Repository
public interface UniversityRepository extends JpaRepository<University,Integer>{
}
