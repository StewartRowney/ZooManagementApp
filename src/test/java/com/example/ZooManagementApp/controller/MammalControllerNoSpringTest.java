package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.services.IMammalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MammalControllerNoSpringTest {

    IMammalService mockMammalService;
    MammalController uut;

    @BeforeEach
    void beforeEach(){
        mockMammalService = Mockito.mock(IMammalService.class);
        uut = new MammalController(mockMammalService);
    }

    @Test
    void test_GetAllMammals_ValidRequest() {
        uut.getAllMammals();
        verify(mockMammalService, times(1)).findAllMammals();
    }

    @Test
    void test_GetMammalById_ValidRequest() {
        UUID mammalId = UUID.randomUUID();
        uut.getMammalById(mammalId);
        verify(mockMammalService, times(1)).findMammalById(mammalId);
    }
}
