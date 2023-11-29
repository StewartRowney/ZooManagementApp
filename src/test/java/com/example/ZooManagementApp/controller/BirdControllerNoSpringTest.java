package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Bird;
import com.example.ZooManagementApp.services.IBirdService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class BirdControllerNoSpringTest {

    private final IBirdService mockBirdService = mock(IBirdService.class);
    private final BirdController uut = new BirdController(mockBirdService);
    private final Bird bird = new Bird();
    private final UUID birdId = UUID.randomUUID();


    @Test
    void test_GetAllBirds() {
        uut.getAllBirds();
        verify(mockBirdService, times(1)).findAllBirds();
    }

    @Test
    void test_GetBird() {
        uut.getBird(birdId);
        verify(mockBirdService, times(1)).findBirdById(birdId);
    }

    @Test
    void test_AddBird() {
        uut.addBird(bird);
        verify(mockBirdService, times(1)).addBird(bird);
    }

    @Test
    void test_UpdateBird() {
        uut.updateBird(bird);
        verify(mockBirdService, times(1)).updateBird(bird);
    }

    @Test
    void test_DeleteBird() {
        uut.deleteBird(birdId);
        verify(mockBirdService, times(1)).deleteBird(birdId);
    }
}
