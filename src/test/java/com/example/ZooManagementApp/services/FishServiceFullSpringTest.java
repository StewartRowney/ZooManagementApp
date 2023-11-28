package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.entities.Fish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FishServiceFullSpringTest {

    @MockBean
    IAnimalRepository mockAnimalRepository;

    @Autowired
    IFishService uut;

    @Test
    void testVerifyRepositoryInvokesGetAllFish() {
        uut.findAllFish();
        verify(mockAnimalRepository, times(1)).findAllFish();
    }

    @Test
    void verifyIfRepositoryInvokesFindById() {
        UUID id= UUID.randomUUID();
        when(mockAnimalRepository.findFishById(id)).thenReturn(Optional.of(new Fish()));
        uut.findFishById(id);
        verify(mockAnimalRepository,times(1)).findFishById(id);
    }

    @Test
    void test_UpdateFish_ValidRequest(){
        Fish fish = createAFish();
        UUID id = fish.getId();
        when(mockAnimalRepository.existsById(any())).thenReturn(true);
        uut.updateFishWithPut(fish);
        verify(mockAnimalRepository, times(1)).save(fish);
    }

    private Fish createAFish() {
        return null;
    }

    @Test
    void test_UpdateFish_InvalidRequest_NoId(){
        Fish fish = new Fish();
        assertThrows(ResponseStatusException.class,() -> uut.updateFishWithPut(null));
    }

    @Test
    void test_UpdateFish_InvalidRequest_IdNotInDatabase(){
        Fish fish = createAFish();
        when(mockAnimalRepository.existsById(any())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.updateFishWithPut(fish));
    }
}
