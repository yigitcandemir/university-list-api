package com.example.project.controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.project.entity.Campus;
import com.example.project.entity.University;
import com.example.project.service.CampusService;
import com.example.project.service.DepartmentService;
import com.example.project.service.FacultyService;
import com.example.project.service.UniversityService;






@Controller
@RequestMapping("/ui")
public class AdminUIController {
    private final UniversityService universityService;
    private final CampusService campusService;
    private final FacultyService facultyService;
    private final DepartmentService departmentService;

    
    public AdminUIController(UniversityService universityService, CampusService campusService, FacultyService facultyService, DepartmentService departmentService){
        this.universityService = universityService;
        this.campusService = campusService;
        this. facultyService = facultyService;
        this.departmentService = departmentService;
    }
    
    //UNIVERSITY
    @GetMapping
    public String dashboard(Model model) {                                                          //Admin ana sayfası için (dashboard)
        model.addAttribute("countUniversities", universityService.getAllUniversities().size());
        return "admin/dashboard";
    }
    
    @GetMapping("/universities")
    public String universities(Model model) {                                                       //Universite listeleme ve ekleme sayfası için
        model.addAttribute("universities", universityService.getAllUniversities());
        model.addAttribute("form", new University());
        return "admin/university";
    }


    @PostMapping("/universities")
    public String createUniversity(@ModelAttribute("form") University u) {                          //Üniversite eklemek için yapı
        universityService.createUniversity(u, "admin");
        return "redirect:/ui/universities";
    }

    @PostMapping("/universities/{id}/delete")
    public String deleteUniversity(@PathVariable int id) {                                          //Üniversite silmek için yapı
        universityService.softDelete(id, "admin");
        return "redirect:/ui/universities";
    }


    @GetMapping("/universities/{id}/edit")
    public String editUniversity(@PathVariable int id, Model model) {                               //Üniversite düzenleme akışı için
        University uni =universityService.getById(id);
        if(uni == null){
            return "redirect:/ui/universities?notfound";
        }
        model.addAttribute("form", uni);
        model.addAttribute("universities", universityService.getAllUniversities());
        return "admin/university";
    }
    

    @PostMapping("/universities/{id}/edit")
    public String updateUniversity(@PathVariable int id, @ModelAttribute("form") University u, @RequestParam(value = "logo", required = false) MultipartFile logo) {    //Üniversite güncellemek için yapı
        if(logo != null && !logo.isEmpty()){
            try {
                universityService.updateLogo(id, logo);
            } catch (IOException e) {
                return "redirect:/ui/universities?logoError";
            }
        }

        universityService.updateUniversity(id, u, "admin");
        return "redirect:/ui/universities";
    }



    //CAMPUS
    @GetMapping("/campuses")
    public String campuses(Model model) {
        model.addAttribute("campuses", campusService.getAllCampuses());
        model.addAttribute("universities", universityService.getAllUniversities());
        Campus form = new Campus();
        form.setUniversity(new University()); 
        model.addAttribute("form", form);
        return "admin/campus";
    }

    @PostMapping("/campuses")
    public String createCampus(@ModelAttribute("form") Campus c, Principal p) {
        campusService.createCampuses(c, p != null ? p.getName() : "admin");
        return "redirect:/ui/campuses";
    }

    @GetMapping("/campuses/{id}/edit")
    public String editCampusForm(@PathVariable int id, Model model) {
        Campus c = campusService.getById(id);
        if (c == null) return "redirect:/ui/campuses?notfound";

        model.addAttribute("campuses", campusService.getAllCampuses());
        model.addAttribute("universities", universityService.getAllUniversities());
        model.addAttribute("form", c); 
        return "admin/campus";
    }

    @PostMapping("/campuses/{id}/edit")
    public String editCampus(@PathVariable int id, @ModelAttribute Campus c, Principal p) {
        campusService.updateCampus(id, c, p != null ? p.getName() : "admin");
        return "redirect:/ui/campuses";
    }
    
    @PostMapping("/campuses/{id}/delete")
    public String deleteCampus(@PathVariable int id, Principal p) {
        campusService.softDelete(id, p != null ? p.getName() : "admin");
        return "redirect:/ui/campuses";
    }
}
