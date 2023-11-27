package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.services.IZooService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
}