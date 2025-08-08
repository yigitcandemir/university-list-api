package com.example.project.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.entity.Faculty;
import com.example.project.repository.FacultyRepository;

@Service
public class FacultyService {

    @Autowired
    public FacultyRepository facultyRepository;

    public List<Faculty> getAllFaculties(){
        return facultyRepository.findByDeletedFalse();
    }

    public Faculty getById(int id){
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty createFaculties(Faculty faculty, String createdBy){
        faculty.setCreatedAt(LocalDateTime.now());
        faculty.setCreatedBy(createdBy);
        return facultyRepository.save(faculty);
    }

    public Faculty updateFaculty(int id, Faculty updated, String updatedBy){
        Faculty existing = getById(id);
        if(existing != null){
            existing.setName(updated.getName());
            existing.setTelephone(updated.getTelephone());
            existing.setDean(updated.getDean());
            existing.setCampus(updated.getCampus());

            existing.setUpdatedAt(LocalDateTime.now());
            existing.setUpdatedBy(updatedBy);

            return facultyRepository.save(existing);
        }
        return null;
    }

    public void softDelete(int id, String deletedBy){
        Faculty faculty = getById(id);
        if(faculty != null){
            faculty.setDeleted(true);
            faculty.setDeletedAt(LocalDateTime.now());
            faculty.setDeleteBy(deletedBy);
            facultyRepository.save(faculty);
        }
    }
}
