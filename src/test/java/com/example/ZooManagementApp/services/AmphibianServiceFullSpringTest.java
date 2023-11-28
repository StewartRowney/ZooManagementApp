package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.data.ZooRepository;
import com.example.ZooManagementApp.entities.Amphibian;
import com.example.ZooManagementApp.entities.Insect;
import com.example.ZooManagementApp.entities.Mammal;
import com.example.ZooManagementApp.entities.Zoo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@SpringBootTest
public class AmphibianServiceFullSpringTest {

    @MockBean
    IAnimalRepository mockAnimalRepository;
    
    @MockBean
    ZooRepository zooRepository;

    @Autowired
    IAmphibianService uut;

    private Amphibian amphibian;
    private UUID amphibianId;
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    @BeforeEach
    void beforeEach(){
        this.amphibian = new Amphibian();
        this.amphibianId= UUID.randomUUID();
    }

    @Test
    void testVerifyRepositoryInvokesGetAllAmphibians() {
        uut.findAllAmphibians();
        verify(mockAnimalRepository, times(1)).findAllAmphibians();
    }
    
    @Test
    void test_findAmphibianById() {
        when(mockAnimalRepository.findAmphibianById(amphibianId)).thenReturn(Optional.of(amphibian));
        uut.findAmphibianById(amphibianId);
        verify(mockAnimalRepository,times(1)).findAmphibianById(amphibianId);
    }

    @Test
    void test_findAmphibianById_nullId(){
        assertThrows(ResponseStatusException.class,() -> uut.findAmphibianById(null));
    }

    @Test
    void test_AddAmphibian_ValidRequest() {
        amphibian.setZoo(createZoo());
        when(zooRepository.existsById(any(UUID.class))).thenReturn(true);
        uut.addAmphibian(amphibian);
        verify(mockAnimalRepository, times(1)).save(amphibian);
    }

    @Test
    void test_AddAmphibian_InvalidRequest_ZooNotInDatabase() {
        amphibian.setZoo(createZoo());
        when(zooRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.addAmphibian(amphibian));
    }

    @Test
    void test_AddAmphibian_InvalidRequest_HasId() {
        Amphibian amphibian = createAmphibian();
        assertThrows(ResponseStatusException.class, () -> uut.addAmphibian(amphibian));
    }

    @Test
    void test_AddAmphibian_InvalidRequest_NullAmphibian() {
        assertThrows(ResponseStatusException.class, () -> uut.addAmphibian(null));
    }


    @Test
    void test_UpdateAmphibian_InvalidRequest_NoId(){
        assertThrows(ResponseStatusException.class,() -> uut.updateAmphibian(null));
    }

    @Test
    void test_UpdateAmphibian_InvalidRequest_IdNotInDatabase(){
        when(mockAnimalRepository.existsById(any())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.updateAmphibian(createAmphibian()));
    }

    @Test
    void test_DeleteAmphibian_ValidRequest_InDatabase() {
        Amphibian amphibian = createAmphibian();
        when(mockAnimalRepository.existsById(any())).thenReturn(true);
        uut.deleteAmphibian(amphibian.getId());
        verify(mockAnimalRepository, times(1)).deleteById(amphibian.getId());
    }

    @Test
    void test_DeleteAmphibian_ValidRequest_NotInDatabase() {
        when(mockAnimalRepository.existsById(any())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.deleteAmphibian(createAmphibian().getId()));
    }

    @Test
    void test_DeleteAmphibian_InvalidRequest_HasNoId() {
        assertThrows(ResponseStatusException.class,() -> uut.deleteAmphibian(null));
    }

    @Test
    void test_UpdateAmphibian_ValidRequest_InDatabase() {
        Amphibian amphibian = createAmphibian();
        when(mockAnimalRepository.existsById(amphibian.getId())).thenReturn(true);
        when(zooRepository.existsById(any())).thenReturn(true);
        uut.updateAmphibian(amphibian);
        verify(mockAnimalRepository, times(1)).save(any(Amphibian.class));
    }

    @Test
    void test_UpdateAmphibian_ValidRequest_NotInDatabase() {
        Amphibian amphibian = createAmphibian();
        when(mockAnimalRepository.existsById(amphibian.getId())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.updateAmphibian(amphibian));
    }

    @Test
    void test_UpdateAmphibian_HasNoId() {
        Amphibian amphibian = new Amphibian(new Zoo(), "Terry", "Snail", LocalDate.of(1990,12,18),
                "Anywhere", "Stone cold killer", "Humans", "Has killed multiple zookeepers", false, false);
        assertThrows(ResponseStatusException.class,() -> uut.updateAmphibian(amphibian));
    }


    @Test
    void test_UpdateAmphibian_ValidRequest() {
        Amphibian amphibian = createAmphibian();
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(true);
        when(zooRepository.existsById(any(UUID.class))).thenReturn(true);
        uut.updateAmphibian(amphibian);
        verify(mockAnimalRepository, times(1)).save(amphibian);
    }

    @Test
    void test_UpdateAmphibian_InvalidRequest_AmphibianNotInDatabase() {
        amphibian.setZoo(createZoo());
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.updateAmphibian(amphibian));
    }

    @Test
    void test_UpdateMammal_InvalidRequest_ZooNotInDatabase() {
        Amphibian amphibian = createAmphibian();
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(true);
        when(zooRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.updateAmphibian(amphibian));
    }

    @Test
    void test_UpdateAmphibian_InvalidRequest_HasNoId() {
        assertThrows(ResponseStatusException.class, () -> uut.updateAmphibian(amphibian));
    }

    @Test
    void test_UpdateAmphibian_InvalidRequest_NullAmphibian() {
         assertThrows(ResponseStatusException.class, () -> uut.updateAmphibian(null));
    }

    private Amphibian createAmphibian() {
        String json = """
                {
                  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                  "name": "string",
                  "speciesName": "string",
                  "birthDate": "28-11-2021",
                  "habitat": "string",
                  "behaviour": "string",
                  "foodType": "string",
                  "extraInformation": "string",
                  "isPoisonous": true,
                  "makesNoise": true
                }""";

        try {
            Amphibian amphibian = objectMapper.readValue(json, Amphibian.class);
            amphibian.setZoo(createZoo());
            return amphibian;
        } catch (JsonProcessingException e) {
            return new Amphibian();
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
