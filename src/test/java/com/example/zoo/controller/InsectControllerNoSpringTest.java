package com.example.zoo.controller;

import com.example.zoo.entities.Insect;
import com.example.zoo.services.IInsectService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class InsectControllerNoSpringTest {

    private final IInsectService mockInsectService = mock(IInsectService.class);
    private final InsectController uut = new InsectController(mockInsectService);
    private final Insect insect = new Insect();
    private final UUID insectId = UUID.randomUUID();


    @Test
    void test_GetAllInsects() {
        uut.getAllInsects();
        verify(mockInsectService, times(1)).findAllInsects();
    }

    @Test
    void test_GetInsect() {
        uut.getInsect(insectId);
        verify(mockInsectService, times(1)).findInsectById(insectId);
    }

    @Test
    void test_AddInsect() {
        uut.addInsect(insect);
        verify(mockInsectService, times(1)).addInsect(insect);
    }

    @Test
    void test_UpdateInsect() {
        uut.updateInsect(insect);
        verify(mockInsectService, times(1)).updateInsect(insect);
    }

    @Test
    void test_DeleteInsect() {
        uut.deleteInsect(insectId);
        verify(mockInsectService, times(1)).deleteInsect(insectId);
    }
}
