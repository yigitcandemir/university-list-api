package com.example.project.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

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
import com.example.project.entity.Department;
import com.example.project.entity.Faculty;
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
    
    
    //Faculty
    public static final String RETURN_PAGE_FACULTY = "redirect:/ui/faculties";
    @GetMapping("/faculties")
    public String faculties(@RequestParam(required=false) Integer universityId, Model model) {
        Faculty form = new Faculty();
        form.setCampus(new Campus()); 
        model.addAttribute("faculties", facultyService.getAllFaculties());
        model.addAttribute("universities", universityService.getAllUniversities());

        var campuses = (universityId != null) ? campusService.getByUniversity(universityId) : java.util.Collections.<Campus>emptyList();
        
        model.addAttribute("campuses",campuses);
        model.addAttribute("form", form);
        model.addAttribute("selectedUniversityId", universityId);
        return "admin/faculty";
    }

    @PostMapping("/faculties")
    public String createFaculty(@ModelAttribute("form") Faculty f, Principal p) {
        facultyService.createFaculties(f, p != null ? p.getName() : "admin");
        return RETURN_PAGE_FACULTY;
    }

    @GetMapping("/faculties/{id}/edit")
    public String editFacultyForm(@PathVariable int id, Model model) { 
        Faculty f = facultyService.getById(id);
        if(f == null) return RETURN_PAGE_FACULTY + "?notfound";

        model.addAttribute("faculties", facultyService.getAllFaculties());
        model.addAttribute("universities", universityService.getAllUniversities());
        Integer selectedUniversityId = null;
        if(f.getCampus() != null && f.getCampus().getUniversity()!= null){
            selectedUniversityId = f.getCampus().getUniversity().getId();
        }
        model.addAttribute("selectedUniversityId", selectedUniversityId);

        var campuses = (selectedUniversityId != null) ? campusService.getByUniversity(selectedUniversityId) : java.util.Collections.<Campus>emptyList();
        model.addAttribute("campuses",campuses);

        model.addAttribute("form", f);
        return "admin/faculty";
    }

    @PostMapping("/faculties/{id}/edit")
    public String editFaculty(@PathVariable int id, @ModelAttribute Faculty f, Principal p) {
        facultyService.updateFaculty(id, f, p != null ? p.getName() : "admin");
        return RETURN_PAGE_FACULTY;
    }

    @PostMapping("/faculties/{id}/delete")
    public String deleteFaculty(@PathVariable int id, Principal p) {
        facultyService.softDelete(id, p != null ? p.getName() : "admin");
        return RETURN_PAGE_FACULTY;
    }


    //Department
    public static final String RETURN_PAGE_DEPARTMENT = "redirect:/ui/departments";
    @GetMapping("/departments")
    public String departments(@RequestParam(required = false) Integer universityId, @RequestParam(required = false) Integer campusId, @RequestParam(required = false) Integer facultyId, Model model) {

        model.addAttribute("universities", universityService.getAllUniversities());

        var campuses = (universityId != null) ? campusService.getByUniversity(universityId): java.util.Collections.<Campus>emptyList();
        model.addAttribute("campuses", campuses);

        var faculties = (campusId != null) ? facultyService.getByCampus(campusId) : java.util.Collections.<Faculty>emptyList();
        model.addAttribute("faculties", faculties);

        model.addAttribute("departments", departmentService.getAllDepartments());

        Department form = new Department();
        form.setFaculty(new Faculty());
        model.addAttribute("form", form);

        model.addAttribute("selectedUniversityId", universityId);
        model.addAttribute("selectedCampusId", campusId);
        model.addAttribute("selectedFacultyId", facultyId);

        return "admin/department";
    }

    @PostMapping("/departments")
    public String createDepartment(@ModelAttribute("form") Department d, Principal p, Model model) {
    if (d.getFaculty() == null || d.getFaculty().getId() == null) {

            model.addAttribute("error", "Lütfen bir fakülte seçiniz.");
            model.addAttribute("universities", universityService.getAllUniversities());
            model.addAttribute("campuses", campusService.getAllCampuses());
            model.addAttribute("faculties", facultyService.getAllFaculties());
            model.addAttribute("departments", departmentService.getAllDepartments());
            model.addAttribute("form", d);

            return "admin/department"; 
        }

        departmentService.createDepartments(d, p != null ? p.getName() : "admin");
        return RETURN_PAGE_DEPARTMENT;
    }

    @GetMapping("/departments/{id}/edit")
    public String editDepartmentForm(@PathVariable int id, Model model){
        Department d = departmentService.getById(id);
        if(d == null) return RETURN_PAGE_DEPARTMENT + "?notfound";

        Integer selectedUniversityId = null;
        Integer selectedCampusId = null;
        Integer selectedFacultyId = null;

        if(d.getFaculty() != null){
            selectedFacultyId = d.getFaculty().getId();
            if(d.getFaculty().getCampus() != null){
                selectedCampusId = d.getFaculty().getCampus().getId();
                if(d.getFaculty().getCampus().getUniversity() != null){
                    selectedUniversityId = d.getFaculty().getCampus().getUniversity().getId();
                }
            }
        }
        model.addAttribute("universities", universityService.getAllUniversities());

        List<Campus> campuses = (selectedUniversityId != null) ? campusService.getByUniversity(selectedUniversityId) : java.util.Collections.<Campus>emptyList();
        model.addAttribute("campuses", campuses);

        List<Faculty> faculties = (selectedCampusId != null) ? facultyService.getByCampus(selectedCampusId) : (selectedUniversityId != null ? facultyService.getByUniversity(selectedUniversityId) : java.util.Collections.<Faculty>emptyList());
        model.addAttribute("faculties", faculties);

        model.addAttribute("departments", departmentService.getAllDepartments());

        model.addAttribute("form", d);

        model.addAttribute("selectedUniversityId", selectedUniversityId);
        model.addAttribute("selectedCampusId", selectedCampusId);
        model.addAttribute("selectedFacultyId", selectedFacultyId);

        return "admin/department";
    }

    @PostMapping("/departments/{id}/edit")
    public String editDepartment(@PathVariable int id, @ModelAttribute Department d, Principal p) {
        departmentService.updateDepartment(id, d, p != null ? p.getName() : "admin");
        return RETURN_PAGE_DEPARTMENT;
    }

    @PostMapping("/departments/{id}/delete")
    public String deleteDepartment(@PathVariable int id, Principal p) {
        departmentService.softDelete(id, p != null ? p.getName() : "admin");
        return RETURN_PAGE_DEPARTMENT;
    }
}
