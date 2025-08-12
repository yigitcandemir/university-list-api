package com.example.project.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.entity.Department;
import com.example.project.entity.DepartmentHistory;
import com.example.project.repository.DepartmentHistoryRepository;
import com.example.project.repository.DepartmentRepository;

@Service
public class DepartmentService {
    
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DepartmentHistoryRepository departmentHistoryRepository;

    public List<Department> getAllDepartments(){
        return departmentRepository.findByDeletedFalse();
    }

    public Department getById(int id){
        return departmentRepository.findById(id).orElse(null);
    }

    public Department createDepartments(Department department, String createdBy){
        department.setCreatedAt(LocalDateTime.now());
        department.setCreatedBy(createdBy);
        return departmentRepository.save(department);
    }

    public Department updateDepartment(int id, Department updated, String updatedBy){
        Department existing = getById(id);
        if(existing == null) return null;
        
        DepartmentHistory h = new DepartmentHistory();
        h.setDepartmentId(existing.getId());
        h.setfacultyId(existing.getFaculty().getId());
        h.setName(existing.getName());
        h.setOperationType("UPDATE");
        h.setOperatedBy(updatedBy);
        departmentHistoryRepository.save(h);


        existing.setName(updated.getName());
        existing.setFaculty(updated.getFaculty());

        existing.setUpdatedAt(LocalDateTime.now());
        existing.setUpdatedBy(updatedBy);

        return departmentRepository.save(existing);
        
        
    }

    public void softDelete(int id, String deletedBy){
        Department department = getById(id);
        if(department != null){
            department.setDeleted(true);
            department.setDeletedAt(LocalDateTime.now());
            department.setDeleteBy(deletedBy);
            departmentRepository.save(department);
        }
    }
}
