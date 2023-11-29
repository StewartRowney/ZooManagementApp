package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.entities.Animal;
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
    void verifyIfRepositoryInvokesFindAll() {
        uut.findAllAnimals();
        verify(mockAnimalRepository,times(1)).findAll();
    }

    @Test
    void verifyIfRepositoryInvokesFindById() {
        UUID id= UUID.randomUUID();
        when(mockAnimalRepository.findById(id)).thenReturn(Optional.of(new Animal()));
        uut.findAnimalById(id);
        verify(mockAnimalRepository,times(1)).findById(id);
    }

    @Test
    void verifyIfRepositoryInvokesFindAllAnimalsByIds() throws JsonProcessingException {
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