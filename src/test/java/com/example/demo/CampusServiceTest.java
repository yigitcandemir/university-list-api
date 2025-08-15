package com.example.demo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.project.entity.Campus;
import com.example.project.repository.CampusRepository;
import com.example.project.service.CampusService;

@ExtendWith(MockitoExtension.class)
class CampusServiceTest {
    
    @Mock
    private CampusRepository campusRepository;

    @InjectMocks
    private CampusService campusService;

    @Test
    void getByUniversity(){
        int universityId = 1;
        List<Campus> campus = List.of(new Campus(1,"Gazi Mustafa Kemal Atatürk Kampüsü"), new Campus(2,"Ordular Kampüsü"));
        when(campusRepository.findByUniversityId(universityId)).thenReturn(campus);

        List<Campus> result = campusService.getByUniversity(universityId);

        assertEquals(2, result.size());
        assertEquals("Gazi Mustafa Kemal Atatürk Kampüsü", result.get(0).getName());
        verify(campusRepository).findByUniversityId(universityId);
    }
}
