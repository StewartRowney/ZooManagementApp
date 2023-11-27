package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Zoo;
import com.example.ZooManagementApp.services.IZooService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class ZooControllerNoSpringTest {
    ZooController uut;
    IZooService mockZooService;

    @BeforeEach
    void initialiseFields(){
        mockZooService = Mockito.mock(IZooService.class);
        uut = new ZooController(mockZooService);

    }
    @Test
    void getAllZoos() {
        uut.getAllZoos();
        verify(mockZooService, times(1)).findAllZoos();
    }

    @Test
    void verifyIfRepositoryInvokesFindById() {
        UUID id= UUID.randomUUID();
        when(mockZooService.findZooById(id)).thenReturn((new Zoo()));
        uut.getZooById(id);
        verify(mockZooService,times(1)).findZooById(id);
    }
    @Test
    void verifyIfRepositoryInvokesFindByName() {
        String name= "someZoo";
        when(mockZooService.findZooByName(anyString())).thenReturn((new Zoo()));
        uut.getZooByName(name);
        verify(mockZooService,times(1)).findZooByName(name);
    }
}