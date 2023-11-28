package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.entities.Amphibian;
import com.example.ZooManagementApp.entities.Insect;
import com.example.ZooManagementApp.entities.Zoo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AmphibianServiceFullSpringTest {

    @MockBean
    IAnimalRepository mockAnimalRepository;

    @Autowired
    IAmphibianService uut;

    private Amphibian amphibian;
    private UUID amphibianId;

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
    void test_PostNewAmphibian_ValidRequest(){
        uut.addAmphibian(amphibian);
        verify(mockAnimalRepository, times(1)).save(amphibian);
    }

    @Test
    void test_UpdateAmphibian_ValidRequest(){
        Amphibian amphibian = createAnAmphibian();
        when(mockAnimalRepository.existsById(any())).thenReturn(true);
        uut.updateAmphibian(amphibian);
        verify(mockAnimalRepository, times(1)).save(amphibian);
    }

    @Test
    void test_UpdateAmphibian_InvalidRequest_NoId(){
        assertThrows(ResponseStatusException.class,() -> uut.updateAmphibian(null));
    }

    @Test
    void test_UpdateAmphibian_InvalidRequest_IdNotInDatabase(){
        when(mockAnimalRepository.existsById(any())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.updateAmphibian(createAnAmphibian()));
    }

    @Test
    void test_DeleteAmphibian_ValidRequest_InDatabase() {
        Amphibian amphibian = createAnAmphibian();
        when(mockAnimalRepository.existsById(any())).thenReturn(true);
        uut.deleteAmphibian(amphibian.getId());
        verify(mockAnimalRepository, times(1)).deleteById(amphibian.getId());
    }

    @Test
    void test_DeleteAmphibian_ValidRequest_NotInDatabase() {
        when(mockAnimalRepository.existsById(any())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.deleteAmphibian(createAnAmphibian().getId()));
    }

    @Test
    void test_DeleteAmphibian_InvalidRequest_HasNoId() {
        assertThrows(ResponseStatusException.class,() -> uut.deleteAmphibian(null));
    }

    @Test
    void test_UpdateAmphibian_ValidRequest_InDatabase() {
        Amphibian amphibian = createAnAmphibian();
        when(mockAnimalRepository.existsById(amphibian.getId())).thenReturn(true);
        when(mockAnimalRepository.findById(amphibian.getId())).thenReturn(Optional.of(amphibian));
        uut.updateAmphibian(amphibian);
        verify(mockAnimalRepository, times(1)).save(any(Amphibian.class));
    }

    @Test
    void test_UpdateAmphibian_ValidRequest_NotInDatabase() {
        Amphibian amphibian = createAnAmphibian();
        when(mockAnimalRepository.existsById(amphibian.getId())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.updateAmphibian(amphibian));
    }

    @Test
    void test_UpdateAmphibian_HasNoId() {
        Amphibian amphibian = new Amphibian(new Zoo(), "Terry", "Snail", LocalDate.of(1990,12,18),
                "Anywhere", "Stone cold killer", "Humans", "Has killed multiple zookeepers", false, false);
        assertThrows(ResponseStatusException.class,() -> uut.updateAmphibian(amphibian));
    }

    private Amphibian createAnAmphibian() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String json = "{\n" +
                "    \"id\": \"40ea5519-fcef-4272-b742-e01790ca04c3\",\n" +
                "    \"name\": \"Chester Amphibian\",\n" +
                "    \"location\": \"Upton-by-Chester, Cheshire, England\",\n" +
                "    \"capacity\": 27000,\n" +
                "    \"price\": 19,\n" +
                "    \"dateOpened\": \"10-06-1931\"\n" +
                "  }";
        try{
            return mapper.readValue(json, Amphibian.class);
        } catch (Exception e) {
            return new Amphibian();
        }
    }
}
