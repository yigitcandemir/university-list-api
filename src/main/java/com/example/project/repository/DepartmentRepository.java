package com.example.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.project.entity.Department;

public interface  DepartmentRepository extends JpaRepository<Department, Integer>{
    List<Department> findByDeletedFalse();
    List<Department> findByFaculty_Campus_University_IdAndDeletedFalseOrderByNameAsc(Integer universityId);
    List<Department> findByFaculty_IdAndDeletedFalseOrderByNameAsc(Integer facultyId);
    List<Department> findByFacultyIdAndParentIsNullAndDeletedFalseOrderByNameAsc(Integer facultyId);
    List<Department> findByFacultyIdAndParentIdAndDeletedFalseOrderByNameAsc(Integer facultyId, Integer parentId);
    List<Department> findByFacultyIdAndNameContainingIgnoreCaseAndDeletedFalseOrderByNameAsc(Integer facultyId, String q);

    @EntityGraph(attributePaths = "children")
    @Query("select d from Department d where d.faculty.id = :facultyId and d.parent is null and d.deleted = false")
    List<Department> fetchRootsWithChildren(@Param("facultyId") Integer facultyId);
}
