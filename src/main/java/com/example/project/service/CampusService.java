package com.example.project.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.entity.Campus;
import com.example.project.entity.CampusHistory;
import com.example.project.repository.CampusHistoryRepository;
import com.example.project.repository.CampusRepository;

@Service
public class CampusService {
    
    @Autowired
    private CampusRepository campusRepository;
    @Autowired
    private CampusHistoryRepository campusHistoryRepository;

    public List<Campus> getAllCampuses(){
        return campusRepository.findByDeletedFalse();
    }

    public Campus getById(int id){
        return campusRepository.findById(id).orElse(null);
    }

    public Campus createCampuses(Campus campus, String createdBy){
        campus.setCreatedAt(LocalDateTime.now());
        campus.setCreatedBy(createdBy);
        return campusRepository.save(campus);
    }

    public List<Campus> getByUniversity(Integer universityId){
        return campusRepository.findByUniversityId(universityId);
    }

    public Campus updateCampus(int id, Campus updated, String updatedBy){
        Campus existing = getById(id);
        if(existing == null) return null;

        CampusHistory h = new CampusHistory();
        h.setCampusId(existing.getId());
        h.setUniversityId(existing.getUniversity().getId());
        h.setName(existing.getName());
        h.setCity(existing.getCity());
        h.setDistrict(existing.getDistrict());
        h.setAddress(existing.getAddress());
        h.setActionType("UPDATE");
        h.setActionBy(updatedBy);
        campusHistoryRepository.save(h);

        existing.setName(updated.getName());
        existing.setCity(updated.getCity());
        existing.setDistrict(updated.getDistrict());
        existing.setAddress(updated.getAddress());
        existing.setUniversity(updated.getUniversity());

        existing.setUpdatedAt(LocalDateTime.now());
        existing.setUpdatedBy(updatedBy);

        return campusRepository.save(existing);
        
        
    }

    public void softDelete(int id, String deletedBy){
        Campus campus = getById(id);
        if(campus != null){
            campus.setDeleted(true);
            campus.setDeletedAt(LocalDateTime.now());
            campus.setDeletedBy(deletedBy);
            campusRepository.save(campus);
        }
    }
}
