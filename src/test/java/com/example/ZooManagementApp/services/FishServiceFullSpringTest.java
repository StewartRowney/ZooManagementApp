package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.entities.Fish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
public class FishServiceFullSpringTest {

    @MockBean
    IAnimalRepository mockAnimalRepository;

    @Autowired
    IFishService uut;

    @Test
    void testVerifyRepositoryInvokesGetAllFish() {
        uut.findAllFish();
        verify(mockAnimalRepository, times(1)).findAllFish();
    }

    @Test
    void verifyIfRepositoryInvokesFindById() {
        UUID id= UUID.randomUUID();
        when(mockAnimalRepository.findFishById(id)).thenReturn(Optional.of(new Fish()));
        uut.findFishById(id);
        verify(mockAnimalRepository,times(1)).findFishById(id);
    }
}
