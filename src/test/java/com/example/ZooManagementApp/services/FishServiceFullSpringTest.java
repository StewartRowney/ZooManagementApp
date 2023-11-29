package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.data.ZooRepository;
import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.entities.Fish;
import com.example.ZooManagementApp.entities.Zoo;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @MockBean
    ZooRepository mockZooRepository;

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
    void test_FindFishById_InvalidRequest_NullId(){
        assertThrows(ResponseStatusException.class, () -> uut.findFishById(null));
    }

    @Test
    void test_UpdateFish_ValidRequest() {
        Fish fish = createAFish();
        fish.setZoo(createZoo());
        when(mockAnimalRepository.existsById(any())).thenReturn(true);
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(true);
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

    @Test
    void test_UpdateFish_InvalidRequest_ZooNotInDatabase() {
        Fish fish = createAFish();
        fish.setZoo(createZoo());
        when(mockAnimalRepository.existsById(any())).thenReturn(true);
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(false);
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
    void test_DeleteFish_ValidRequest_NullID(){
        assertThrows(ResponseStatusException.class, () -> uut.removeFishById(null));
    }

    @Test
    void test_DeleteFish_ValidRequest_NotInDatabase() {
        Fish fish = createAFish();
        when(mockAnimalRepository.existsById(any())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.removeFishById(fish.getId()));
    }

    @Test
    void test_AddFish_ValidRequest(){
        Fish fish = new Fish();
        fish.setZoo(createZoo());
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(true);
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(true);
        uut.addFish(fish);
        verify(mockAnimalRepository,times(1)).save(fish);
    }
    @Test
    void test_AddFish_InvalidRequest_ZooNotInDatabase() {
        Fish fish = new Fish();
        fish.setZoo(createZoo());
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(true);
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.addFish(fish));
    }

    @Test
    void test_AddFish_InvalidRequest_HasId() {
        Fish fish = createAFish();
        assertThrows(ResponseStatusException.class, () -> uut.addFish(fish));
    }

    @Test
    void test_AddFish_InvalidRequest_NullFish() {
        assertThrows(ResponseStatusException.class, () -> uut.addFish(null));
    }

    private Zoo createZoo() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String json = """
                 {
                    "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                    "name": "string",
                    "location": "string",
                    "capacity": 0,
                    "price": 0,
                    "dateOpened": "12-05-1999"
                  }""";
        try {
            return mapper.readValue(json, Zoo.class);
        } catch (JsonProcessingException e) {
            return new Zoo();
        }
    }
}
