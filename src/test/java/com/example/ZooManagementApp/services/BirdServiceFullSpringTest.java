package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class BirdServiceFullSpringTest {

    @MockBean
    IAnimalRepository mockAnimalRepository;

    @Autowired
    IBirdService uut;

    @Test
    void testVerifyRepositoryInvokesGetAllBirds() {
        uut.findAllBirds();
        verify(mockAnimalRepository, times(1)).findAllBirds();
    }
}
