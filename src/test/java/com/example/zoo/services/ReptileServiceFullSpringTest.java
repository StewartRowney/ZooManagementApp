package com.example.zoo.services;

import com.example.zoo.data.IAnimalRepository;
import com.example.zoo.data.ZooRepository;
import com.example.zoo.entities.Reptile;
import com.example.zoo.entities.Zoo;
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

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
class ReptileServiceFullSpringTest {

    @MockBean
    IAnimalRepository mockAnimalRepository;
    
    @MockBean
    ZooRepository zooRepository;
    
    @Autowired
    IReptileService uut;

    private Reptile reptile;
    private UUID reptileId;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    @BeforeEach
    void beforeEach(){
        this.reptile = new Reptile();
        this.reptileId= UUID.randomUUID();
    }

    @Test
    void testVerifyRepositoryInvokesGetAllReptiles() {
        uut.findAllReptiles();
        verify(mockAnimalRepository, times(1)).findAllReptiles();
    }

    @Test
    void test_findReptileById() {
        when(mockAnimalRepository.findReptileById(reptileId)).thenReturn(Optional.of(reptile));
        uut.findReptileById(reptileId);
        verify(mockAnimalRepository,times(1)).findReptileById(reptileId);
    }

    @Test
    void test_findReptileById_nullId(){
        assertThrows(ResponseStatusException.class,() -> uut.findReptileById(null));
    }

    @Test
    void test_AddReptile_ValidRequest() {
        reptile.setZoo(createZoo());
        when(zooRepository.existsById(any(UUID.class))).thenReturn(true);
        uut.addReptile(reptile);
        verify(mockAnimalRepository, times(1)).save(reptile);
    }

    @Test
    void test_AddReptile_InvalidRequest_ZooNotInDatabase() {
        reptile.setZoo(createZoo());
        when(zooRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.addReptile(reptile));
    }

    @Test
    void test_AddReptile_InvalidRequest_HasId() {
        Reptile reptile = createReptile();
        assertThrows(ResponseStatusException.class, () -> uut.addReptile(reptile));
    }

    @Test
    void test_AddReptile_InvalidRequest_NullReptile() {
        assertThrows(ResponseStatusException.class, () -> uut.addReptile(null));
    }


    @Test
    void test_UpdateReptile_InvalidRequest_NoId(){
        assertThrows(ResponseStatusException.class,() -> uut.updateReptile(null));
    }

    @Test
    void test_UpdateReptile_InvalidRequest_IdNotInDatabase(){
        reptile = createReptile();
        when(mockAnimalRepository.existsById(any())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.updateReptile(reptile));
    }

    @Test
    void test_DeleteReptile_ValidRequest_InDatabase() {
        Reptile reptile = createReptile();
        when(mockAnimalRepository.existsById(any())).thenReturn(true);
        uut.deleteReptile(reptile.getId());
        verify(mockAnimalRepository, times(1)).deleteById(reptile.getId());
    }

    @Test
    void test_DeleteReptile_ValidRequest_NotInDatabase() {
        when(mockAnimalRepository.existsById(any())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.deleteReptile(reptileId));
    }

    @Test
    void test_DeleteReptile_InvalidRequest_HasNoId() {
        assertThrows(ResponseStatusException.class,() -> uut.deleteReptile(null));
    }

    @Test
    void test_UpdateReptile_ValidRequest_InDatabase() {
        Reptile reptile = createReptile();
        when(mockAnimalRepository.existsById(reptile.getId())).thenReturn(true);
        when(zooRepository.existsById(any())).thenReturn(true);
        uut.updateReptile(reptile);
        verify(mockAnimalRepository, times(1)).save(any(Reptile.class));
    }

    @Test
    void test_UpdateReptile_ValidRequest_NotInDatabase() {
        Reptile reptile = createReptile();
        when(mockAnimalRepository.existsById(reptile.getId())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.updateReptile(reptile));
    }

    @Test
    void test_UpdateReptile_HasNoId() {
        Reptile reptile = new Reptile(new Zoo(), "Sally", "Python", LocalDate.of(2020, 10, 12),
        "Jungle", "Docile", "Meat", "Only eats twice a month", false, false, true);
        assertThrows(ResponseStatusException.class,() -> uut.updateReptile(reptile));
    }


    @Test
    void test_UpdateReptile_ValidRequest() {
        Reptile reptile = createReptile();
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(true);
        when(zooRepository.existsById(any(UUID.class))).thenReturn(true);
        uut.updateReptile(reptile);
        verify(mockAnimalRepository, times(1)).save(reptile);
    }

    @Test
    void test_UpdateReptile_InvalidRequest_ReptileNotInDatabase() {
        reptile.setZoo(createZoo());
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.updateReptile(reptile));
    }

    @Test
    void test_UpdateMammal_InvalidRequest_ZooNotInDatabase() {
        Reptile reptile = createReptile();
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(true);
        when(zooRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.updateReptile(reptile));
    }

    @Test
    void test_UpdateReptile_InvalidRequest_HasNoId() {
        assertThrows(ResponseStatusException.class, () -> uut.updateReptile(reptile));
    }

    @Test
    void test_UpdateReptile_InvalidRequest_NullReptile() {
        assertThrows(ResponseStatusException.class, () -> uut.updateReptile(null));
    }

    private Reptile createReptile() {
        String json = """
                {
                  "id": "9b2d9232-9385-4707-965f-e5a90cbcfc88",
                  "name": "string",
                  "speciesName": "string",
                  "birthDate": "28-11-2021",
                  "habitat": "string",
                  "behaviour": "string",
                  "foodType": "string",
                  "extraInformation": "string",
                  "hasShell": false,
                  "isColdBlooded": false,
                  "hasLegs": true
                }""";

        try {
            Reptile reptile = objectMapper.readValue(json, Reptile.class);
            reptile.setZoo(createZoo());
            return reptile;
        } catch (JsonProcessingException e) {
            return new Reptile();
        }
    }

    private Zoo createZoo() {
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
            return objectMapper.readValue(json, Zoo.class);
        } catch (JsonProcessingException e) {
            return new Zoo();
        }
    }
}
