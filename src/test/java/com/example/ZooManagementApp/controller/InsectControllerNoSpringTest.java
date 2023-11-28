package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Insect;
import com.example.ZooManagementApp.entities.Mammal;
import com.example.ZooManagementApp.services.IInsectService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InsectControllerNoSpringTest {
    private final IInsectService mockInsectService = mock(IInsectService.class);
    private final InsectController uut = new InsectController(mockInsectService);

    @Test
    void test_getAllInsects_ValidRequest() {
        uut.getAllInsects();
        verify(mockInsectService, times(1)).findAllInsects();
    }

    @Test
    void test_getInsectById_ValidRequest() {
        UUID insectId = UUID.randomUUID();
        uut.getInsectById(insectId);
        verify(mockInsectService, times(1)).findInsectById(insectId);
    }

    @Test
    void test_addInsect_ValidRequest() {
        Insect insect = new Insect();
        uut.addInsect(insect);
        verify(mockInsectService, times(1)).addInsect(insect);
    }

    @Test
    void test_UpdateInsect_ValidRequest() {
        Insect insect = new Insect();
        uut.updateInsect(insect);
        verify(mockInsectService, times(1)).updateInsect(insect);
    }

    @Test
    void test_DeleteInsectById_ValidRequest() {
        UUID insectId = UUID.randomUUID();
        uut.deleteInsectById(insectId);
        verify(mockInsectService, times(1)).deleteInsectById(insectId);
    }
}