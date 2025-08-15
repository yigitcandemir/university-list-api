package com.example.demo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.project.entity.Faculty;
import com.example.project.repository.FacultyRepository;
import com.example.project.service.FacultyService;

@ExtendWith(MockitoExtension.class)
class FacultyServiceTest {
    
    @Mock private FacultyRepository facultyRepository;

    @InjectMocks private FacultyService facultyService;

    @Test
    void softDeleteFaculty(){

        Faculty faculty = new Faculty();

        faculty.setId(10);
        faculty.setName("Bilgisayar Mühendisliği");
        when(facultyRepository.findById(10)).thenReturn(Optional.of(faculty));
        when(facultyRepository.save(any(Faculty.class))).thenAnswer(inv -> inv.getArgument(0));
        facultyService.softDelete(10, "Yetkili");

        assertTrue(faculty.getDeleted());
        assertEquals("Yetkili", faculty.getDeletedBy());
        verify(facultyRepository).findById(10);
        verify(facultyRepository).save(faculty);
    }

}
