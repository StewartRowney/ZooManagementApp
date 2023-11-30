package com.example.zoo.services;

import com.example.zoo.data.IAnimalRepository;
import com.example.zoo.entities.Animal;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class AnimalServiceFullSpringTest {

    @MockBean
    private IAnimalRepository mockAnimalRepository;

    @Autowired
    private IAnimalService uut;

    @Test
    void test_FindAllAnimals_ServiceCalled() {
        uut.findAllAnimals();
        verify(mockAnimalRepository,times(1)).findAll();
    }

    @Test
    void test_findAnimalById_ServiceCalled() {
        UUID id= UUID.randomUUID();
        when(mockAnimalRepository.findById(id)).thenReturn(Optional.of(new Animal()));
        uut.findAnimalById(id);
        verify(mockAnimalRepository,times(1)).findById(id);
    }

    @Test
    void test_findAnimalsByListOfId_ServiceCalled() throws JsonProcessingException {
        List<UUID> idList = createIdList();
        uut.findAnimalListById(idList);
        verify(mockAnimalRepository,times(1)).findAllById(anyList());
    }

    public List<UUID> createIdList() throws JsonProcessingException {
        List<UUID> idList = new ArrayList<>();
        idList.add(UUID.randomUUID());
        idList.add(UUID.randomUUID());
        idList.add(UUID.randomUUID());
        return idList;
    }
}