package com.example.zoo.controller;

import com.example.zoo.entities.Mammal;
import com.example.zoo.services.IMammalService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

class MammalControllerNoSpringTest {

    private final IMammalService mockMammalService = mock(IMammalService.class);
    private final MammalController uut = new MammalController(mockMammalService);
    private final Mammal mammal = new Mammal();
    private final UUID mammalId = UUID.randomUUID();


    @Test
    void test_GetAllMammals() {
        uut.getAllMammals();
        verify(mockMammalService, times(1)).findAllMammals();
    }

    @Test
    void test_GetMammal() {
        uut.getMammal(mammalId);
        verify(mockMammalService, times(1)).findMammalById(mammalId);
    }

    @Test
    void test_AddMammal() {
        uut.addMammal(mammal);
        verify(mockMammalService, times(1)).addMammal(mammal);
    }

    @Test
    void test_UpdateMammal() {
        uut.updateMammal(mammal);
        verify(mockMammalService, times(1)).updateMammal(mammal);
    }

    @Test
    void test_DeleteMammal() {
        uut.deleteMammal(mammalId);
        verify(mockMammalService, times(1)).deleteMammal(mammalId);
    }
}
