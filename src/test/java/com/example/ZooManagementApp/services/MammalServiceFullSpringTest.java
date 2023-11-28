package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.data.ZooRepository;
import com.example.ZooManagementApp.entities.Mammal;
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
public class MammalServiceFullSpringTest {

    @MockBean
    IAnimalRepository mockAnimalRepository;

    @MockBean
    ZooRepository mockZooRepository;

    @Autowired
    IMammalService uut;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void test_FindAllMammals_ValidRequest() {
        uut.findAllMammals();
        verify(mockAnimalRepository, times(1)).findAllMammals();
    }

    @Test
    void test_FindMammalById_ValidRequest() {
        UUID mammalId = UUID.randomUUID();
        when(mockAnimalRepository.findMammalById(mammalId)).thenReturn(Optional.of(new Mammal()));
        uut.findMammalById(mammalId);
        verify(mockAnimalRepository, times(1)).findMammalById(mammalId);
    }

    @Test
    void test_FindMammalById_InvalidRequest_NotInDatabase() {
        UUID mammalId = UUID.randomUUID();
        assertThrows(ResponseStatusException.class, () -> uut.findMammalById(mammalId));
    }

    @Test
    void test_FindMammalById_InvalidRequest_NullID() {
        assertThrows(ResponseStatusException.class, () -> uut.findMammalById(null));
    }

    @Test
    void test_AddMammal_ValidRequest() {
        Mammal mammal = new Mammal();
        mammal.setZoo(createZoo());
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(true);
        uut.addMammal(mammal);
        verify(mockAnimalRepository, times(1)).save(mammal);
    }

    @Test
    void test_AddMammal_InvalidRequest_ZooNotInDatabase() {
        Mammal mammal = new Mammal();
        mammal.setZoo(createZoo());
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.addMammal(mammal));
    }

    @Test
    void test_AddMammal_InvalidRequest_HasId() {
        Mammal mammal = createMammal();
        assertThrows(ResponseStatusException.class, () -> uut.addMammal(mammal));
    }

    @Test
    void test_AddMammal_InvalidRequest_NullMammal() {
        assertThrows(ResponseStatusException.class, () -> uut.addMammal(null));
    }

    @Test
    void test_UpdateMammal_ValidRequest() {
        Mammal mammal = createMammal();
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(true);
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(true);
        uut.updateMammal(mammal);
        verify(mockAnimalRepository, times(1)).save(mammal);
    }

    @Test
    void test_UpdateMammal_InvalidRequest_MammalNotInDatabase() {
        Mammal mammal = new Mammal();
        mammal.setZoo(createZoo());
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.updateMammal(mammal));
    }

    @Test
    void test_UpdateMammal_InvalidRequest_ZooNotInDatabase() {
        Mammal mammal = new Mammal();
        mammal.setZoo(createZoo());
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(true);
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.updateMammal(mammal));
    }

    @Test
    void test_UpdateMammal_InvalidRequest_HasNoId() {
        Mammal mammal = new Mammal();
        assertThrows(ResponseStatusException.class, () -> uut.updateMammal(mammal));
    }

    @Test
    void test_UpdateMammal_InvalidRequest_NullMammal() {
        assertThrows(ResponseStatusException.class, () -> uut.updateMammal(null));
    }

    private Mammal createMammal() {
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
                  "hasFur": true,
                  "hasFins": true,
                  "hasHooves": true
                }""";

        try {
            Mammal mammal = mapper.readValue(json, Mammal.class);
            mammal.setZoo(createZoo());
            return mammal;
        } catch (JsonProcessingException e) {
            return new Mammal();
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
            return mapper.readValue(json, Zoo.class);
        } catch (JsonProcessingException e) {
            return new Zoo();
        }
    }
}
