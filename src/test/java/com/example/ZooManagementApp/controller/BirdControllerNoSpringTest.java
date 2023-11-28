package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Bird;
import com.example.ZooManagementApp.entities.Zoo;
import com.example.ZooManagementApp.services.IBirdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class BirdControllerNoSpringTest {

    IBirdService mockBirdService;
    BirdController uut;

    @BeforeEach
    void beforeEach(){
        mockBirdService = Mockito.mock(IBirdService.class);
        uut = new BirdController(mockBirdService);
    }

    @Test
    void testBirdServiceCalledForGetAllBirds() {
        uut.getAllBirds();
        verify(mockBirdService, times(1)).findAllBirds();
    }

    @Test
    void test_findBirdById() {
        UUID id= UUID.randomUUID();
        when(mockBirdService.findBirdById(id)).thenReturn((new Bird()));
        uut.getBirdById(id);
        verify(mockBirdService,times(1)).findBirdById(id);
    }

    @Test
    void test_AddNewZoo(){
        Bird bird = new Bird();
        uut.postNewBird(bird);
        verify(mockBirdService,times(1)).addNewBird(bird);
    }

    @Test
    void test_UpdateZoo(){
        Bird bird = new Bird();
        uut.putABird(bird);
        verify(mockBirdService,times(1)).updateBirdWithPut(bird);
    }

    @Test
    void test_DeleteAZoo(){
        Bird bird = new Bird();
        uut.deleteBirdById(bird.getId());
        verify(mockBirdService,times(1)).removeBirdById(bird.getId());
    }
}
