package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.ZooRepository;
import com.example.ZooManagementApp.entities.Zoo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class ZooServiceFullSpringTest {

    @MockBean
    ZooRepository mockZooRepo;

    @Autowired
    ZooService uut;

    @Test
    void verifyIfRepositoryInvokesFindAll() {
        uut.findAllZoos();
        verify(mockZooRepo,times(1)).findAll();
    }

    @Test
    void verifyIfRepositoryInvokesFindById() {
        UUID id= UUID.randomUUID();
        when(mockZooRepo.findById(id)).thenReturn(Optional.of(new Zoo()));
        uut.findZooById(id);
        verify(mockZooRepo,times(1)).findById(id);
    }
    @Test
    void verifyIfRepositoryInvokesFindByName() {
        String name= "someZoo";
        when(mockZooRepo.findByName(anyString())).thenReturn(Optional.of(new Zoo()));
        uut.findZooByName(name);
        verify(mockZooRepo,times(1)).findByName(name);
    }
}