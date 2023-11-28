package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.entities.Fish;
import com.example.ZooManagementApp.services.IFishService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.Mockito.*;

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

    @Test
    void verifyIfRepositoryInvokesFindById() {
        UUID id= UUID.randomUUID();
        when(mockFishService.findFishById(id)).thenReturn((new Fish()));
        uut.getFishById(id);
        verify(mockFishService,times(1)).findFishById(id);
    }

    @Test
    void test_UpdateFish(){
        Fish fish = new Fish();
        uut.putAFish(fish);
        verify(mockFishService,times(1)).updateFishWithPut(fish);
    }

    @Test
    void test_DeleteAZoo(){
        Fish fish = new Fish();
        uut.deleteZooById(fish.getId());
        verify(mockFishService,times(1)).removeFishById(fish.getId());
    }
}
