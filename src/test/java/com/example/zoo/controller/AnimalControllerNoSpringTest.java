package com.example.zoo.controller;


import com.example.zoo.entities.Animal;
import com.example.zoo.services.IAnimalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;


class AnimalControllerNoSpringTest {

    private final IAnimalService mockAnimalService = mock(IAnimalService.class);
    private final AnimalController uut = new AnimalController(mockAnimalService);
    private final UUID id= UUID.randomUUID();


    @Test
    void test_getAllAnimals_ValidRequest() {
        uut.getAllAnimals();
        verify(mockAnimalService, times(1)).findAllAnimals();
    }

    @Test
    void test_getAnimalById_ServiceCalled() {
        when(mockAnimalService.findAnimalById(id)).thenReturn((new Animal()));
        uut.getAnimalById(id);
        verify(mockAnimalService,times(1)).findAnimalById(id);
    }

    @Test
    void test_postAllAnimalsByIds_ValidRequest() throws JsonProcessingException {
        uut.getAnimalListById(createIdList());
        verify(mockAnimalService, times(1)).findAnimalListById(anyList());
    }

    public List<UUID> createIdList() throws JsonProcessingException {
        List<UUID> idList = new ArrayList<>();
        idList.add(UUID.randomUUID());
        idList.add(UUID.randomUUID());
        idList.add(UUID.randomUUID());
        return idList;
    }

}