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
    void test_getAnimalsByZooId_ServiceCalled() {
        uut.getAnimalListByZooId(UUID.randomUUID());
        verify(mockAnimalService,times(1)).findAnimalListByZooId(any(UUID.class));
    }

    @Test
    void test_postAllAnimalsByIds_ValidRequest(){
        uut.getAnimalListById(createIdList());
        verify(mockAnimalService, times(1)).findAnimalListById(anyList());
    }

    @Test
    void test_deleteAnimalsByIds_ValidRequest(){
        uut.deleteAnimalsByIds(createIdList());
        verify(mockAnimalService, times(1)).deleteAnimalsByIds(anyList());
    }

    public List<UUID> createIdList(){
        List<UUID> idList = new ArrayList<>();
        idList.add(UUID.randomUUID());
        idList.add(UUID.randomUUID());
        idList.add(UUID.randomUUID());
        return idList;
    }

}