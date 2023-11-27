package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.data.ZooRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class AnimalServiceFullSpringTest {

    @MockBean
    private IAnimalRepository mockAnimalRepository;

    @Autowired
    private IAnimalService uut;

    @Test
    void verifyIfRepositoryInvokesFindAll() {
        uut.findAllAnimals();
        verify(mockAnimalRepository,times(1)).findAll();
    }

}