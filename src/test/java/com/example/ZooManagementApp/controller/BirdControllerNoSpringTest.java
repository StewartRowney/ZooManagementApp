package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.services.IBirdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BirdControllerNoSpringTest {

    IBirdService mockBirdService;
    BirdController uut;

    @BeforeEach
    void beforeEach(){
        mockBirdService = Mockito.mock(IBirdService.class);
        uut = new BirdController(mockBirdService);
    }

    @Test
    void testMammalServiceCalledForGetAllMammals() {
        uut.getAllBirds();
        verify(mockBirdService, times(1)).findAllBirds();
    }
}
