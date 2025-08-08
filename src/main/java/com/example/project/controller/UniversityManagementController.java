package com.example.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.entity.Campus;
import com.example.project.entity.Department;
import com.example.project.entity.Faculty;
import com.example.project.entity.University;
import com.example.project.service.CampusService;
import com.example.project.service.DepartmentService;
import com.example.project.service.FacultyService;
import com.example.project.service.UniversityService;

@RestController
@RequestMapping("/api")
public class UniversityManagementController {
    
    @Autowired
    private UniversityService universityService;

    @GetMapping("/universities")
    public List<University> getAllUniversities(){
        return universityService.getAllUniversities();
    }

    @PostMapping("/universities")
    public University createUniversity (@RequestBody University u, @RequestParam(defaultValue="admin") String createdBy) {
        return universityService.createUniversity(u, createdBy);
    }

    @PutMapping("/universities/{id}")
    public University updateUniversity(@PathVariable int id, @RequestBody University university, @RequestParam(defaultValue="admin") String updatedBy){
        return universityService.updateUniversity(id, university, updatedBy);
    }

    @DeleteMapping("/universities/{id}")
    public void deleteUniversity(@PathVariable int id, @RequestParam(defaultValue="admin") String deletedBy){
        universityService.softDelete(id, deletedBy);
    }


    @Autowired
    private CampusService campusService;

    @GetMapping("/campus")
    public List<Campus> getAllCampuses() {
        return campusService.getAllCampuses();
    }
    
    @PostMapping("/campus")
    public Campus createCampus(@RequestBody Campus c, @RequestParam(defaultValue="admin") String createdBy) {
        return campusService.createCampuses(c, createdBy);
    }

    @PutMapping("/campus/{id}")
    public Campus updateCampus(@PathVariable int id, @RequestBody Campus c, @RequestParam(defaultValue="admin") String updatedBy) {
        return campusService.updateCampus(id, c, updatedBy);
    }

    @DeleteMapping("/campus/{id}")
    public void deleteCampus(@PathVariable int id, @RequestParam(defaultValue="admin") String deletedBy){
        campusService.softDelete(id, deletedBy);
    }
    

    @Autowired
    private FacultyService facultyService;

    @GetMapping("/faculty")
    public List<Faculty> getAllFaculties() {
        return facultyService.getAllFaculties();
    }
    
    @PostMapping("/faculty")
    public Faculty createFaculty(@RequestBody Faculty f, @RequestParam(defaultValue="admin") String createdBy) {
        return facultyService.createFaculties(f, createdBy);
    }

    @PutMapping("faculty/{id}")
    public Faculty updateFaculty(@PathVariable int id, @RequestBody Faculty f, @RequestParam(defaultValue="admin") String updatedBy) {
        return facultyService.updateFaculty(id, f, updatedBy);
    }

    @DeleteMapping("/faculty/{id}")
    public void deleteFaculty(@PathVariable int id, @RequestParam(defaultValue="admin") String deletedBy){
        facultyService.softDelete(id, deletedBy);
    }


    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/department")
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }
    
    @PostMapping("/department")
    public Department createDepartment(@RequestBody Department d, @RequestParam(defaultValue="admin") String createdBy) {
        return departmentService.createDepartments(d, createdBy);
    }

    @PutMapping("/department/{id}")
    public Department updateDepartment(@PathVariable int id, @RequestBody Department d, @RequestParam(defaultValue="admin") String updatedBy) {
        return departmentService.updateDepartment(id, d, updatedBy);
    }

    @DeleteMapping("/department/{id}")
    public void deleteDepartment(@PathVariable int id, @RequestParam(defaultValue="admin") String deletedBy){
        departmentService.softDelete(id, deletedBy);
    }
    
}
