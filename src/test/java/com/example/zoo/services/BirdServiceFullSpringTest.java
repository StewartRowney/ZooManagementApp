package com.example.zoo.services;

import com.example.zoo.data.IAnimalRepository;
import com.example.zoo.data.ZooRepository;
import com.example.zoo.entities.Bird;
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

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest
class BirdServiceFullSpringTest {

    @MockBean
    IAnimalRepository mockAnimalRepository;
    @MockBean
    ZooRepository mockZooRepository;

    @Autowired
    IBirdService uut;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private Bird bird;
    private final UUID birdId = UUID.randomUUID();

    @BeforeEach
    public void beforeEach() {
        bird = new Bird();
    }

    @Test
    void test_FindAllBirds_ValidRequest() {
        uut.findAllBirds();
        verify(mockAnimalRepository, times(1)).findAllBirds();
    }

    @Test
    void test_FindBirdById_ValidRequest() {
        when(mockAnimalRepository.findBirdById(birdId)).thenReturn(Optional.of(new Bird()));
        uut.findBirdById(birdId);
        verify(mockAnimalRepository, times(1)).findBirdById(birdId);
    }

    @Test
    void test_FindBirdById_InvalidRequest_NotInDatabase() {
        assertThrows(ResponseStatusException.class, () -> uut.findBirdById(birdId));
    }

    @Test
    void test_FindBirdById_InvalidRequest_NullID() {
        assertThrows(ResponseStatusException.class, () -> uut.findBirdById(null));
    }

    @Test
    void test_AddBird_ValidRequest() {
        bird.setZoo(createZoo());
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(true);
        uut.addBird(bird);
        verify(mockAnimalRepository, times(1)).save(bird);
    }

    @Test
    void test_AddBird_InvalidRequest_ZooNotInDatabase() {
        bird.setZoo(createZoo());
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.addBird(bird));
    }

    @Test
    void test_AddBird_InvalidRequest_HasId() {
        bird = createBird();
        assertThrows(ResponseStatusException.class, () -> uut.addBird(bird));
    }

    @Test
    void test_AddBird_InvalidRequest_NullBird() {
        assertThrows(ResponseStatusException.class, () -> uut.addBird(null));
    }

    @Test
    void test_UpdateBird_ValidRequest() {
        bird = createBird();
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(true);
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(true);
        uut.updateBird(bird);
        verify(mockAnimalRepository, times(1)).save(bird);
    }

    @Test
    void test_UpdateBird_InvalidRequest_BirdNotInDatabase() {
        bird = createBird();
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.updateBird(bird));
    }

    @Test
    void test_UpdateBird_InvalidRequest_ZooNotInDatabase() {
        bird = createBird();
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(true);
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.updateBird(bird));
    }

    @Test
    void test_UpdateBird_InvalidRequest_HasNoId() {
        bird = new Bird();
        assertThrows(ResponseStatusException.class, () -> uut.updateBird(bird));
    }

    @Test
    void test_UpdateBird_InvalidRequest_NullBird() {
        assertThrows(ResponseStatusException.class, () -> uut.updateBird(null));
    }

    @Test
    void test_DeleteBirdById_ValidRequest() {
        when(mockAnimalRepository.existsById(birdId)).thenReturn(true);
        uut.deleteBird(birdId);
        verify(mockAnimalRepository, times(1)).deleteById(birdId);
    }

    @Test
    void test_DeleteById_InvalidRequest_NotInDatabase() {
        when(mockAnimalRepository.existsById(birdId)).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.deleteBird(birdId));
    }

    @Test
    void test_DeleteById_InvalidRequest_NullId() {
        assertThrows(ResponseStatusException.class, () -> uut.deleteBird(null));
    }


    private Bird createBird() {
            String json = """
                    {
                        "id": "9183d69e-97dc-4138-92ca-3d1cc0a1aef2",
                        "name": "Henry",
                        "speciesName": "Pigeon",
                        "birthDate": "18-01-2022",
                        "habitat": "Woods",
                        "behaviour": "calm and chirpy",
                        "foodType": "seeds",
                        "extraInformation": "Annoying and noisy!",
                        "canMimicSound": false,
                        "nocturnal": false
                    }""";

            try {
                Bird bird = mapper.readValue(json, Bird.class);
                bird.setZoo(createZoo());
                return bird;
            } catch (JsonProcessingException e) {
                return new Bird();
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
                    "dateOpened": "1999-05-12"
                  }""";
        try {
            return mapper.readValue(json, Zoo.class);
        } catch (JsonProcessingException e) {
            return new Zoo();
        }
    }

}
