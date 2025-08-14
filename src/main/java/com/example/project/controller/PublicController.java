package com.example.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.project.service.CampusService;
import com.example.project.service.DepartmentService;
import com.example.project.service.FacultyService;
import com.example.project.service.UniversityService;


@Controller
public class PublicController {
    
    @Autowired
    private UniversityService universityService;
    @Autowired
    private CampusService campusService;
    @Autowired
    private FacultyService facultyService;
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/")
    public String mainPage(Model model) {
        return "redirect:/universities";
    }

    @GetMapping("/universities")
    public String listUniversities(@RequestParam(value = "q", required = false) String q,Model model) {             
        var universities = universityService.searchByName(q);                                         
        model.addAttribute("universities", universities);
        model.addAttribute("q",q);
        model.addAttribute("count", universities.size());
        return "public/universities";
    }

    @GetMapping("/universities/{id}")
    public String universityDetails(@PathVariable Integer id, Model model) {
        model.addAttribute("university", universityService.getById(id));
        model.addAttribute("campuses", campusService.getByUniversity(id));
        model.addAttribute("faculties", facultyService.getByUniversity(id));
        model.addAttribute("departments", departmentService.getByUniversity(id));
        return "public/university_details";
    }

    @GetMapping("/campuses/{id}")
    public String campusDetail(@PathVariable Integer id, Model model) {
        model.addAttribute("campus", campusService.getById(id));
        model.addAttribute("faculties", facultyService.getByCampus(id));
        return "public/campus_detail";
    }

    @GetMapping("/faculties/{id}")
    public String facultyDetail(@PathVariable Integer id, Model model) {
        model.addAttribute("faculty", facultyService.getById(id));
        model.addAttribute("departments", departmentService.getByFaculty(id));
        return "public/faculty_detail";
    }

    @GetMapping("/departments/{id}")
    public String departmentDetail(@PathVariable Integer id, Model model) {
        model.addAttribute("department", departmentService.getById(id));
        return "public/department_detail";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

}
