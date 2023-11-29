package com.example.ZooManagementApp.controller;


import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.services.IAnimalService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;


class AnimalControllerNoSpringTest {

    private final IAnimalService mockAnimalService = mock(IAnimalService.class);
    private final AnimalController uut = new AnimalController(mockAnimalService);

    @Test
    void test_getAllAnimals_ValidRequest() {
        uut.getAllAnimals();
        verify(mockAnimalService, times(1)).findAllAnimals();
    }

    @Test
    void verifyIfRepositoryInvokesFindById() {
        UUID id= UUID.randomUUID();
        when(mockAnimalService.findAnimalById(id)).thenReturn((new Animal()));
        uut.getAnimalById(id);
        verify(mockAnimalService,times(1)).findAnimalById(id);
    }

}