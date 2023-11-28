package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.entities.Fish;
import com.example.ZooManagementApp.entities.Zoo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
        UUID id = UUID.randomUUID();
        when(mockAnimalRepository.findFishById(id)).thenReturn(Optional.of(new Fish()));
        uut.findFishById(id);
        verify(mockAnimalRepository, times(1)).findFishById(id);
    }

    @Test
    void test_UpdateFish_ValidRequest() {
        Fish fish = createAFish();
        when(mockAnimalRepository.existsById(any())).thenReturn(true);
        uut.updateFishWithPut(fish);
        verify(mockAnimalRepository, times(1)).save(fish);
    }

    @Test
    void test_UpdateFish_InvalidRequest_NoId() {
        Fish fish = new Fish();
        assertThrows(ResponseStatusException.class, () -> uut.updateFishWithPut(null));
    }

    @Test
    void test_UpdateFish_InvalidRequest_IdNotInDatabase() {
        Fish fish = new Fish();
        when(mockAnimalRepository.existsById(any())).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.updateFishWithPut(fish));
    }

    private Fish createAFish() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String json = "{\n" +
                "    \"id\": \"40ea5519-fcef-4272-b742-e01790ca04c3\",\n" +
                "    \"name\": \"Chester Zoo\",\n" +
                "    \"location\": \"Upton-by-Chester, Cheshire, England\",\n" +
                "    \"capacity\": 27000,\n" +
                "    \"price\": 19,\n" +
                "    \"dateOpened\": \"10-06-1931\"\n" +
                "  }";
        try {
            return mapper.readValue(json, Fish.class);
        } catch (Exception e) {
            return new Fish();
        }
    }

    @Test
    void test_DeleteFish_ValidRequest_InDatabase() {
        Fish fish = createAFish();
        when(mockAnimalRepository.existsById(any())).thenReturn(true);
        uut.removeFishById(fish.getId());
        verify(mockAnimalRepository, times(1)).deleteById(fish.getId());
    }

    @Test
    void test_DeleteFish_ValidRequest_NotInDatabase() {
        Fish fish = createAFish();
        when(mockAnimalRepository.existsById(any())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.removeFishById(fish.getId()));
    }
    @Test
    void test_AddFish_InvalidRequest_ZooNotInDatabase() {
        Fish fish = createAFish();
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.addFish(fish));
    }

    @Test
    void test_AddFish_InvalidRequest_HasId() {
        Fish fish = createAFish();
        assertThrows(ResponseStatusException.class, () -> uut.addFish(fish));
    }

    @Test
    void test_AddFish_InvalidRequest_NullMammal() {
        assertThrows(ResponseStatusException.class, () -> uut.addFish(null));
    }
}
