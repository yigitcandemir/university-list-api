package com.example.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.entity.University;
import com.example.project.repository.UniversityRepository;

@RestController
@RequestMapping("/api")
public class UniversityManagementController {
    
    @Autowired
    private UniversityRepository universityRepository;

    @GetMapping("/universities")
    public List<University> getAllUniversities(){
        return universityRepository.findAll();
    }
}
