package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.services.IFishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FishControllerNoSpringTest {

    IFishService mockFishService;
    FishController uut;

    @BeforeEach
    void beforeEach(){
        mockFishService = Mockito.mock(IFishService.class);
        uut = new FishController(mockFishService);
    }

    @Test
    void testFishServiceCalledForGetAllFish() {
        uut.getAllFish();
        verify(mockFishService, times(1)).findAllFish();
    }
}
