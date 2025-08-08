package com.example.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.entity.University;
import com.example.project.repository.UniversityRepository;

@Service
public class UniversityService {
    
    @Autowired
    private UniversityRepository universityRepository;

    public List<University> getAllUniversities(){
        List<University> all = universityRepository.findAll();
        List<University> active = new ArrayList<>();

        for(University u : all){
            if(!u.isDeleted()){
                active.add(u);
            }
        }
        return active;
    }

    public University getById(int id){
        return universityRepository.findById(id).orElse(null);
    }

    public University createUniversity(University university, String createdBy){
        university.setCreatedAt(LocalDateTime.now());
        university.setCreatedBy(createdBy);
        return universityRepository.save(university);
    }

    public University updateUniversity(int id, University updated, String updatedBy){
        University existing = getById(id);
        if(existing != null){
            existing.setName(updated.getName());
            existing.setWebsite(updated.getWebsite());
            existing.setUpdatedAt(LocalDateTime.now());
            existing.setUpdatedBy(updatedBy);
            return universityRepository.save(existing);
        }
        return null;
    }

    public void softDelete(int id, String deletedBy){
        University university = getById(id);
        if(university != null){
            university.setDeleted(true);
            university.setDeletedAt(LocalDateTime.now());
            university.setDeletedBy(deletedBy);
            universityRepository.save(university);
        }
    }
}
