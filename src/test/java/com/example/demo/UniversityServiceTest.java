package com.example.demo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.project.entity.University;
import com.example.project.repository.UniversityRepository;
import com.example.project.service.UniversityService;

@ExtendWith(MockitoExtension.class)
class UniversityServiceTest {
    @Mock 
    private UniversityRepository universityRepository;

    @InjectMocks
    private UniversityService universityService;

    @Test
    void createUniversityTest(){

        University u = new University();
        u.setName("Yiğit Üniversitesi");

        when(universityRepository.save(any(University.class))).thenAnswer(inv -> inv.getArgument(0));

        University saved = universityService.createUniversity(u, "adminUser");

        assertEquals("Yiğit Üniversitesi", saved.getName());
        assertEquals("adminUser", saved.getCreatedBy());
        verify(universityRepository).save(any(University.class));

    }
}
