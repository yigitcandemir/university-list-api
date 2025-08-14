package com.example.project.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
            if(!u.getDeleted()){
                active.add(u);
            }
        }
        return active;
    }

    public List<University> searchByName(String q){
        if(q == null || q.isBlank()) return getAllUniversities();
        return universityRepository.findByNameContainingIgnoreCase(q.trim());
    }

    public University getById(int id){
        return universityRepository.findById(id).orElse(null);
    }

    public void updateLogo(int id, MultipartFile file) throws IOException {
        if(file == null || file.isEmpty()) return;
        University uni = universityRepository.findById(id).orElseThrow(() -> new RuntimeException("Üniversite bulunamadı"));

        String ct = file.getContentType();
        if(ct == null || !(ct.equals("image/png")|| ct.equals("image/jpeg") || ct.equals("image/jpg")|| ct.equals("image/webp")))
            throw new IllegalArgumentException("Yalnızca PNG/JPEG/JPG/WEBP yükleyin.");
        
        if(file.getSize() > 256 * 1024)
            throw new IllegalArgumentException("Logo en fazla 256KB olmalı.");

        uni.setLogoBase64(Base64.getEncoder().encodeToString(file.getBytes()));
        uni.setLogoContentType(file.getContentType());

        universityRepository.save(uni);
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
