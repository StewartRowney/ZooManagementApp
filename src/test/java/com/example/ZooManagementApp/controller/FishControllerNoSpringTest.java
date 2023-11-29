package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Fish;
import com.example.ZooManagementApp.services.IFishService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class FishControllerNoSpringTest {

    private final IFishService mockFishService = mock(IFishService.class);
    private final FishController uut = new FishController(mockFishService);
    private final Fish fish = new Fish();
    private final UUID fishId = UUID.randomUUID();


    @Test
    void test_GetAllFish() {
        uut.getAllFish();
        verify(mockFishService, times(1)).findAllFish();
    }

    @Test
    void test_GetFish() {
        uut.getFish(fishId);
        verify(mockFishService, times(1)).findFishById(fishId);
    }

    @Test
    void test_AddFish() {
        uut.addFish(fish);
        verify(mockFishService, times(1)).addFish(fish);
    }

    @Test
    void test_UpdateFish() {
        uut.updateFish(fish);
        verify(mockFishService, times(1)).updateFish(fish);
    }

    @Test
    void test_DeleteFish() {
        uut.deleteFish(fishId);
        verify(mockFishService, times(1)).deleteFish(fishId);
    }
}
