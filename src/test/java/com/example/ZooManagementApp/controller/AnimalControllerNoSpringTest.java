package com.example.ZooManagementApp.controller;


import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.entities.Zoo;
import com.example.ZooManagementApp.services.IAnimalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;


class AnimalControllerNoSpringTest {

    private final IAnimalService mockAnimalService = mock(IAnimalService.class);
    private final AnimalController uut = new AnimalController(mockAnimalService);
    private final ObjectMapper mapper = new ObjectMapper();

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