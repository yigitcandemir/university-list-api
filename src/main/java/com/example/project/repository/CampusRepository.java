package com.example.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.project.entity.Campus;


@Repository
public interface  CampusRepository extends JpaRepository<Campus, Integer>{
    List<Campus> findByDeletedFalse();
    List<Campus> findByUniversityId(Integer universityId);
}
