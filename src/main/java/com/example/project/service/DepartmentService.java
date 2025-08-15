package com.example.project.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.project.entity.Department;
import com.example.project.entity.DepartmentHistory;
import com.example.project.entity.Faculty;
import com.example.project.repository.DepartmentHistoryRepository;
import com.example.project.repository.DepartmentRepository;
import com.example.project.repository.FacultyRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class DepartmentService {
    
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DepartmentHistoryRepository departmentHistoryRepository;
    @Autowired
    private FacultyRepository facultyRepository;

    public List<Department> roots(Integer facultyId){
        return departmentRepository.fetchRootsWithChildren(facultyId);
    }

    public List<Department> search(Integer facultyId, String q){
        if(q == null || q.isBlank())
            return departmentRepository.findByFacultyIdAndParentIsNullAndDeletedFalseOrderByNameAsc(facultyId);
        return departmentRepository.findByFacultyIdAndNameContainingIgnoreCaseAndDeletedFalseOrderByNameAsc(facultyId, q);
    }

    public List<Department> getAllDepartments(){
        return departmentRepository.findByDeletedFalse();
    }

    public List<Department> getByUniversity(Integer universityId){
        return departmentRepository.findByFaculty_Campus_University_IdAndDeletedFalseOrderByNameAsc(universityId);
    }

    public Department getById(int id){
        return departmentRepository.findById(id).orElse(null);
    }

    public List<Department> getByFaculty(Integer facultyId) {
        return departmentRepository.findByFaculty_IdAndDeletedFalseOrderByNameAsc(facultyId);
    }

    @Transactional
    public Department createDepartments(Department department, String createdBy){
        Faculty faculty = facultyRepository.findById(department.getFaculty().getId()).orElseThrow(() -> new NoSuchElementException("Faculty bulunamadı: " + department.getFaculty().getId()));
        Department entity = new Department();
        entity.setName(department.getName());
        entity.setFaculty(faculty);

        if(department.getParent() != null && department.getParent().getId() != null){
            Department parent = departmentRepository.findById(department.getParent().getId()).orElseThrow(() -> new NoSuchElementException("Parent department bulunamadı: " + department.getParent().getId()));
            ensureSameFaculty(faculty,parent);
            entity.setParent(parent);
        }

        entity.setUpdatedAt(LocalDateTime.now());
        entity.setUpdatedBy(createdBy);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setCreatedBy(createdBy);
        Department saved = departmentRepository.save(entity);

        log(saved,"CREATE", createdBy);
        
        return saved;
    }


    @Transactional
    public Department updateDepartment(int id, Department updated, String updatedBy){
        Department existing = departmentRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Department bulunamadı: " + id));

        Faculty targetFaculty = facultyRepository.findById(updated.getFaculty().getId()).orElseThrow(()-> new NoSuchElementException("Faculty bulunamadı: " + updated.getFaculty().getId()));
        if(existing == null) return null;
        
        Department newParent = null;
        if (updated.getParent() != null && updated.getParent().getId() != null) {
            newParent = departmentRepository.findById(updated.getParent().getId())
                    .orElseThrow(() -> new NoSuchElementException("Parent department bulunamadı: " + updated.getParent().getId()));
            ensureSameFaculty(targetFaculty, newParent);
            ensureNoCycle(existing, newParent);
        }

        existing.setName(updated.getName());
        existing.setFaculty(targetFaculty);
        existing.setParent(newParent);
        existing.setUpdatedBy(updatedBy);
        existing.setUpdatedAt(LocalDateTime.now());

        Department saved = departmentRepository.save(existing);
        log(saved, "UPDATE", updatedBy);

        return saved;
    }

    @Transactional
    public Department moveDepartment(int id, Integer newParentId, String updatedBy) {
        Department node = departmentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Department bulunamadı: " + id));

        Department newParent = null;
        if (newParentId != null) {
            newParent = departmentRepository.findById(newParentId)
                    .orElseThrow(() -> new NoSuchElementException("Parent department bulunamadı: " + newParentId));
            ensureSameFaculty(node.getFaculty(), newParent);
            ensureNoCycle(node, newParent);
        }

        node.setParent(newParent);
        node.setUpdatedBy(updatedBy);
        node.setUpdatedAt(LocalDateTime.now());

        Department saved = departmentRepository.save(node);
        log(saved, "UPDATE", updatedBy); 
        return saved;
    }

    @Transactional
    public void softDelete(int id, String deletedBy) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Department bulunamadı: " + id));

        if (Boolean.TRUE.equals(department.getDeleted())) {
            return; 
        }

        department.setDeleted(true);
        department.setDeletedAt(LocalDateTime.now());
        department.setDeletedBy(deletedBy);

        departmentRepository.save(department);

        
        log(department, "DELETE", deletedBy);
    }



    private void ensureSameFaculty(Faculty targetFaculty, Department parent) {
        if (!Objects.equals(parent.getFaculty().getId(), targetFaculty.getId())) {
            throw new IllegalStateException("Parent farklı bir fakülteye ait olamaz.");
        }
    }

    
    private void ensureNoCycle(Department node, Department newParent) {
        if (newParent == null) return;
        if (Objects.equals(node.getId(), newParent.getId())) {
            throw new IllegalStateException("Bir departman kendi altına taşınamaz.");
        }
        for (Department p = newParent; p != null; p = p.getParent()) {
            if (Objects.equals(p.getId(), node.getId())) {
                throw new IllegalStateException("Döngü tespit edildi (cycle).");
            }
        }
    }

    
    @PersistenceContext EntityManager em;
    private void log(Department d, String op, String actor) {
        DepartmentHistory h = new DepartmentHistory();
        h.setDepartmentId(d.getId());
        h.setFacultyId(d.getFaculty() != null ? d.getFaculty().getId() : null);
        h.setName(d.getName());
        h.setOperationType(op);          
        h.setOperatedBy(actor);

       
        if (d.getParent() != null) {
        h.setParent(em.getReference(Department.class, d.getParent().getId()));
        } else {
            h.setParent(null);
        }
        departmentHistoryRepository.save(h);
    }


}
