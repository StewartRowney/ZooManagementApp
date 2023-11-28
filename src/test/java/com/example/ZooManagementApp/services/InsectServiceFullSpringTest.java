package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.entities.Insect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class InsectServiceFullSpringTest {

    @MockBean
    private IAnimalRepository mockAnimalRepository;

    @Autowired
    private IInsectService uut;

    @Test
    void test_GetAllInsects_ValidRequest() {
        uut.findAllInsects();
        verify(mockAnimalRepository,times(1)).findAllInsects();
    }

    @Test
    void test_GetInsectById_ValidRequest() {
        UUID insectId = UUID.randomUUID();
        when(mockAnimalRepository.findInsectById(insectId)).thenReturn(Optional.of(new Insect()));
        uut.findInsectById(insectId);
        verify(mockAnimalRepository,times(1)).findInsectById(insectId);
    }

    @Test
    void test_GetInsectByID_InvalidRequest_NotInDatabase() {
        UUID insectId = UUID.randomUUID();
        assertThrows(ResponseStatusException.class, () -> uut.findInsectById(insectId));
    }

    @Test
    void test_GetInsectByID_InvalidRequest_NullId() {
        assertThrows(ResponseStatusException.class, () -> uut.findInsectById(null));
    }

}
