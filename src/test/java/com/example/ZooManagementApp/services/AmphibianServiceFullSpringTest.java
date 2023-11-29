package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.data.ZooRepository;
import com.example.ZooManagementApp.entities.Amphibian;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
public class AmphibianServiceFullSpringTest {

    @MockBean
    IAnimalRepository mockAnimalRepository;
    @MockBean
    ZooRepository mockZooRepository;

    @Autowired
    IAmphibianService uut;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private Amphibian amphibian;
    private final UUID amphibianId = UUID.randomUUID();

    @BeforeEach
    public void beforeEach() {
        amphibian = new Amphibian();
    }

    @Test
    void test_FindAllAmphibians_ValidRequest() {
        uut.findAllAmphibians();
        verify(mockAnimalRepository, times(1)).findAllAmphibians();
    }

    @Test
    void test_FindAmphibianById_ValidRequest() {
        when(mockAnimalRepository.findAmphibianById(amphibianId)).thenReturn(Optional.of(new Amphibian()));
        uut.findAmphibianById(amphibianId);
        verify(mockAnimalRepository, times(1)).findAmphibianById(amphibianId);
    }

    @Test
    void test_FindAmphibianById_InvalidRequest_NotInDatabase() {
        assertThrows(ResponseStatusException.class, () -> uut.findAmphibianById(amphibianId));
    }

    @Test
    void test_FindAmphibianById_InvalidRequest_NullID() {
        assertThrows(ResponseStatusException.class, () -> uut.findAmphibianById(null));
    }

    @Test
    void test_AddAmphibian_ValidRequest() {
        amphibian.setZoo(createZoo());
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(true);
        uut.addAmphibian(amphibian);
        verify(mockAnimalRepository, times(1)).save(amphibian);
    }

    @Test
    void test_AddAmphibian_InvalidRequest_ZooNotInDatabase() {
        amphibian.setZoo(createZoo());
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.addAmphibian(amphibian));
    }

    @Test
    void test_AddAmphibian_InvalidRequest_HasId() {
        amphibian = createAmphibian();
        assertThrows(ResponseStatusException.class, () -> uut.addAmphibian(amphibian));
    }

    @Test
    void test_AddAmphibian_InvalidRequest_NullAmphibian() {
        assertThrows(ResponseStatusException.class, () -> uut.addAmphibian(null));
    }

    @Test
    void test_UpdateAmphibian_ValidRequest() {
        amphibian = createAmphibian();
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(true);
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(true);
        uut.updateAmphibian(amphibian);
        verify(mockAnimalRepository, times(1)).save(amphibian);
    }

    @Test
    void test_UpdateAmphibian_InvalidRequest_AmphibianNotInDatabase() {
        amphibian = createAmphibian();
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.updateAmphibian(amphibian));
    }

    @Test
    void test_UpdateAmphibian_InvalidRequest_ZooNotInDatabase() {
        amphibian = createAmphibian();
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(true);
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.updateAmphibian(amphibian));
    }

    @Test
    void test_UpdateAmphibian_InvalidRequest_HasNoId() {
        amphibian = new Amphibian();
        assertThrows(ResponseStatusException.class, () -> uut.updateAmphibian(amphibian));
    }

    @Test
    void test_UpdateAmphibian_InvalidRequest_NullAmphibian() {
        assertThrows(ResponseStatusException.class, () -> uut.updateAmphibian(null));
    }

    @Test
    void test_DeleteAmphibianById_ValidRequest() {
        when(mockAnimalRepository.existsById(amphibianId)).thenReturn(true);
        uut.deleteAmphibian(amphibianId);
        verify(mockAnimalRepository, times(1)).deleteById(amphibianId);
    }

    @Test
    void test_DeleteById_InvalidRequest_NotInDatabase() {
        when(mockAnimalRepository.existsById(amphibianId)).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.deleteAmphibian(amphibianId));
    }

    @Test
    void test_DeleteById_InvalidRequest_NullId() {
        assertThrows(ResponseStatusException.class, () -> uut.deleteAmphibian(null));
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
            Amphibian amphibian = mapper.readValue(json, Amphibian.class);
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
            return mapper.readValue(json, Zoo.class);
        } catch (JsonProcessingException e) {
            return new Zoo();
        }
    }
}
