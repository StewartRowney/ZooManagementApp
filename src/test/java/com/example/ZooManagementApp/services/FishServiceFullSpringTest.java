package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.data.ZooRepository;
import com.example.ZooManagementApp.entities.Fish;
import com.example.ZooManagementApp.entities.Fish;
import com.example.ZooManagementApp.entities.Zoo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class FishServiceFullSpringTest {

    @MockBean
    IAnimalRepository mockAnimalRepository;
    @MockBean
    ZooRepository mockZooRepository;

    @Autowired
    IFishService uut;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private Fish fish;
    private final UUID fishId = UUID.randomUUID();

    @BeforeEach
    public void beforeEach() {
        fish = new Fish();
    }

    @Test
    void test_FindAllFishs_ValidRequest() {
        uut.findAllFish();
        verify(mockAnimalRepository, times(1)).findAllFish();
    }

    @Test
    void test_FindFishById_ValidRequest() {
        when(mockAnimalRepository.findFishById(fishId)).thenReturn(Optional.of(new Fish()));
        uut.findFishById(fishId);
        verify(mockAnimalRepository, times(1)).findFishById(fishId);
    }

    @Test
    void test_FindFishById_InvalidRequest_NotInDatabase() {
        assertThrows(ResponseStatusException.class, () -> uut.findFishById(fishId));
    }

    @Test
    void test_FindFishById_InvalidRequest_NullID() {
        assertThrows(ResponseStatusException.class, () -> uut.findFishById(null));
    }

    @Test
    void test_AddFish_ValidRequest() {
        fish.setZoo(createZoo());
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(true);
        uut.addFish(fish);
        verify(mockAnimalRepository, times(1)).save(fish);
    }

    @Test
    void test_AddFish_InvalidRequest_ZooNotInDatabase() {
        fish.setZoo(createZoo());
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.addFish(fish));
    }

    @Test
    void test_AddFish_InvalidRequest_HasId() {
        fish = createFish();
        assertThrows(ResponseStatusException.class, () -> uut.addFish(fish));
    }

    @Test
    void test_AddFish_InvalidRequest_NullFish() {
        assertThrows(ResponseStatusException.class, () -> uut.addFish(null));
    }

    @Test
    void test_UpdateFish_ValidRequest() {
        fish = createFish();
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(true);
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(true);
        uut.updateFish(fish);
        verify(mockAnimalRepository, times(1)).save(fish);
    }

    @Test
    void test_UpdateFish_InvalidRequest_FishNotInDatabase() {
        fish = createFish();
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.updateFish(fish));
    }

    @Test
    void test_UpdateFish_InvalidRequest_ZooNotInDatabase() {
        fish = createFish();
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(true);
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.updateFish(fish));
    }

    @Test
    void test_UpdateFish_InvalidRequest_HasNoId() {
        fish = new Fish();
        assertThrows(ResponseStatusException.class, () -> uut.updateFish(fish));
    }

    @Test
    void test_UpdateFish_InvalidRequest_NullFish() {
        assertThrows(ResponseStatusException.class, () -> uut.updateFish(null));
    }

    @Test
    void test_DeleteFishById_ValidRequest() {
        when(mockAnimalRepository.existsById(fishId)).thenReturn(true);
        uut.deleteFish(fishId);
        verify(mockAnimalRepository, times(1)).deleteById(fishId);
    }

    @Test
    void test_DeleteById_InvalidRequest_NotInDatabase() {
        when(mockAnimalRepository.existsById(fishId)).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.deleteFish(fishId));
    }

    @Test
    void test_DeleteById_InvalidRequest_NullId() {
        assertThrows(ResponseStatusException.class, () -> uut.deleteFish(null));
    }

    private Fish createFish() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String json = """
                {
                    "id": "40ea5519-fcef-4272-b742-e01790ca04c3",
                    "name": "Chester Zoo",
                    "location": "Upton-by-Chester, Cheshire, England",
                    "capacity": 27000,
                    "price": 19,
                    "dateOpened": "10-06-1931"
                  }""";
        try {
            Fish newFish = mapper.readValue(json, Fish.class);
            newFish.setZoo(createZoo());
            return newFish;
        } catch (Exception e) {
            return new Fish();
        }
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
