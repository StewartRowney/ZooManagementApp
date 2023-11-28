package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.entities.Bird;
import com.example.ZooManagementApp.entities.Zoo;
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

    @Autowired
    IBirdService uut;

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
    void test_addNewBird_InvalidRequest_NoBird() {
        assertThrows(ResponseStatusException.class,() -> uut.addNewBird(null));
    }

    @Test
    void test_addNewBird_InvalidRequest_BirdHasID() {
        Bird bird = new Bird();
        when(mockAnimalRepository.findById(any())).thenReturn(Optional.of(bird));
        assertThrows(ResponseStatusException.class,() -> uut.addNewBird(bird));
    }
    @Test
    void test_addNewBird_ValidRequest() {
        Bird bird = new Bird();
        Zoo zoo = createAZoo();
        bird.setZoo(zoo);
        uut.addNewBird(bird);
        verify(mockAnimalRepository, times(1)).save(bird);
    }

    @Test
    void test_PostNewBird_ValidRequest(){
        Bird bird = new Bird();
        uut.addNewBird(bird);
        verify(mockAnimalRepository, times(1)).save(bird);
    }

    @Test
    void test_UpdateBird_ValidRequest(){
        Bird bird = createABird();
        UUID id = bird.getId();
        when(mockAnimalRepository.existsById(any())).thenReturn(true);
        uut.updateBirdWithPut(bird);
        //assertThrows(ResponseStatusException.class,() -> uut.updateZooWithPut(bird));
        verify(mockAnimalRepository, times(1)).save(bird);
    }

    @Test
    void test_UpdateBird_InvalidRequest_NoId(){
        Bird bird = new Bird();
        //when(mockAnimalRepository.existsById(any())).thenReturn(false);
        //uut.updateZooWithPut(bird);
        assertThrows(ResponseStatusException.class,() -> uut.updateBirdWithPut(null));
    }

    @Test
    void test_UpdateBird_InvalidRequest_IdNotInDatabase(){
        Bird bird = new Bird();
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
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String json = "{\n" +
                "    \"id\": \"40ea5519-fcef-4272-b742-e01790ca04c3\",\n" +
                "    \"name\": \"Chester Bird\",\n" +
                "    \"location\": \"Upton-by-Chester, Cheshire, England\",\n" +
                "    \"capacity\": 27000,\n" +
                "    \"price\": 19,\n" +
                "    \"dateOpened\": \"10-06-1931\"\n" +
                "  }";
        try{
            return mapper.readValue(json, Bird.class);
        } catch (Exception e) {
            return new Bird();
        }
    }

    private Zoo createAZoo() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String json = "{\n" +
                "    \"id\": \"40ea5519-fcef-4272-b742-e01790ca04c3\",\n" +
                "    \"name\": \"Chester Zoo\",\n" +
                "    \"location\": \"Upton-by-Chester, Cheshire, England\",\n" +
                "    \"capacity\": 27000,\n" +
                "    \"price\": 19,\n" +
                "    \"dateOpened\": \"10-06-1931\"\n" +
                "  }";
        try{
            return mapper.readValue(json, Zoo.class);
        } catch (Exception e) {
            return new Zoo();
        }


    }
}
