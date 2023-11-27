package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
class InsectServiceFullSpringTest {

    @MockBean
    private IAnimalRepository mockAnimalRepository;

    @Autowired
    private IInsectService uut;

    @Test
    void verifyIfRepositoryInvokesFindAll() {
        uut.findAllInsects();
        verify(mockAnimalRepository,times(1)).findAllInsects();
    }

}
