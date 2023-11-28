package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.data.ZooRepository;
import com.example.ZooManagementApp.entities.Bird;
import com.example.ZooManagementApp.entities.Zoo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BirdServiceFullSpringTest {

    @MockBean
    IAnimalRepository mockAnimalRepository;

    @MockBean
    ZooRepository zooRepository;

    @Autowired
    IBirdService uut;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    @Test
    void testVerifyRepositoryInvokesGetAllBirds() {
        uut.findAllBirds();
        verify(mockAnimalRepository, times(1)).findAllBirds();
    }

    @Test
    void test_FindBirdByID_ValidRequest() {
        UUID id= UUID.randomUUID();
        when(mockAnimalRepository.findBirdById(id)).thenReturn(Optional.of(new Bird()));
        uut.findBirdById(id);
        verify(mockAnimalRepository,times(1)).findBirdById(id);
    }
    @Test
    void test_FindBirdByID_IDNotFound() {
        UUID id= UUID.randomUUID();
        assertThrows(EntityNotFoundException.class,() -> uut.findBirdById(id));
//        when(mockAnimalRepository.findById(id)).thenReturn(Optional.of(new Bird()));
//        uut.findBirdById(id);
//        verify(mockAnimalRepository,times(1)).findById(id);
    }

    @Test
    void test_findBirdById_nullId(){
        assertThrows(ResponseStatusException.class,() -> uut.findBirdById(null));
    }

    @Test
    void test_addNewBird_InvalidRequest_NullBird() {
        assertThrows(ResponseStatusException.class,() -> uut.addNewBird(null));
    }
    @Test
    void test_addNewBird_InvalidRequest_BirdHasID() {
        Bird bird = createABird();
        assertThrows(ResponseStatusException.class,() -> uut.addNewBird(bird));
    }

    @Test
    void test_addNewBird_InvalidRequest_ZooNotInDataBase() {
        Bird bird = new Bird();
        Zoo zoo = createAZoo();
        bird.setZoo(zoo);
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.addNewBird(bird));
    }
    @Test
    void test_addNewBird_ValidRequest() {
        Bird bird = new Bird();
        bird.setZoo(createAZoo());

        when(mockAnimalRepository.existsById(bird.getId())).thenReturn(true);
        when(zooRepository.existsById(any(UUID.class))).thenReturn(true);
        uut.addNewBird(bird);
        verify(mockAnimalRepository, times(1)).save(bird);
    }

    @Test
    void test_UpdateBird_ValidRequest(){
        Bird bird = createABird();
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(true);
        when(zooRepository.existsById(any(UUID.class))).thenReturn(true);
        uut.updateBirdWithPut(bird);
        //assertThrows(ResponseStatusException.class,() -> uut.updateZooWithPut(bird));
        verify(mockAnimalRepository, times(1)).save(bird);
    }

    @Test
    void test_UpdateBird_InvalidRequest_NoId(){
        Bird bird = new Bird();
        when(mockAnimalRepository.existsById(any())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.updateBirdWithPut(null));
    }

    @Test
    void test_UpdateBird_InvalidRequest_IdNotInDatabase(){
        Bird bird = createABird();
        when(mockAnimalRepository.existsById(any())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.updateBirdWithPut(bird));
    }

    @Test
    void test_DeleteBird_ValidRequest_InDatabase() {
        Bird bird = new Bird();
        UUID id = UUID.randomUUID();
        when(mockAnimalRepository.existsById(any())).thenReturn(true);
        uut.removeBirdById(id);
        verify(mockAnimalRepository, times(1)).deleteById(id);
    }

    @Test
    void test_DeleteBird_ValidRequest_NotInDatabase() {
        Bird zoo = createABird();
        when(mockAnimalRepository.existsById(any())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.removeBirdById(zoo.getId()));
    }

    @Test
    void test_DeleteBird_InvalidRequest_HasNoId() {
        assertThrows(ResponseStatusException.class,() -> uut.removeBirdById(null));
    }


    private Bird createABird() {
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
                bird.setZoo(createAZoo());
                return bird;
            } catch (JsonProcessingException e) {
                return new Bird();
            }
        }


    private Zoo createAZoo() {
        //ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
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
//TODO: Figure out why one line is not covered in the tests.