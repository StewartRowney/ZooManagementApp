package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.data.ZooRepository;
import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.entities.Zoo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class ZooServiceFullSpringTest {

    @MockBean
    ZooRepository mockZooRepo;
    @MockBean
    IAnimalRepository mockAnimalRepo;

    @Autowired
    ZooService uut;

    @Test
    void verifyIfRepositoryInvokesFindAll() {
        uut.findAllZoos();
        verify(mockZooRepo,times(1)).findAll();
    }

    @Test
    void verifyIfRepositoryInvokesFindById() {
        UUID id= UUID.randomUUID();
        when(mockZooRepo.findById(id)).thenReturn(Optional.of(new Zoo()));
        uut.findZooById(id);
        verify(mockZooRepo,times(1)).findById(id);
    }

    @Test
    void test_findZooById_nullId(){
        assertThrows(ResponseStatusException.class,() -> uut.findZooById(null));
    }

    @Test
    void verifyIfRepositoryInvokesFindByName() {
        String name= "someZoo";
        when(mockZooRepo.findByName(anyString())).thenReturn(Optional.of(new Zoo()));
        uut.findZooByName(name);
        verify(mockZooRepo,times(1)).findByName(name);
    }

    @Test
    void verifyMockRepoCallsSaveWhenAddingNewZoo() {
        Zoo zoo = new Zoo();
        uut.addNewZoo(zoo);
        verify(mockZooRepo, times(1)).save(zoo);
    }

    @Test
    void test_PostNewZoo_ValidRequest(){
        Zoo zoo = new Zoo();
        uut.addNewZoo(zoo);
        verify(mockZooRepo, times(1)).save(zoo);
    }

    @Test
    void test_AddNewZoo_InvalidRequest_ZooHasId(){
        Zoo zoo = createAZoo();
        when(mockZooRepo.existsById(any())).thenReturn(true);
        assertThrows(ResponseStatusException.class,() -> uut.addNewZoo(zoo));
    }

    @Test
    void test_AddListOfZoos_ValidRequest() throws JsonProcessingException {
        List<Zoo> zoos = new ArrayList<>();
        Zoo zoo = new Zoo();
        zoos.add(zoo);
        uut.addListOfZoos(zoos);
        verify(mockZooRepo, times(1)).save(zoo);
    }

    @Test
    void test_AddListOFZoos_InvalidRequest_1GoodZoo2BadZoo(){

        List<Zoo> zoos = new ArrayList<>();
        Zoo zoo1 = createAZoo();
        zoos.add(zoo1);
        Zoo zoo2 = new Zoo();
        Zoo zoo3 = createAZoo();
        zoos.add(zoo2);
        zoos.add(zoo3);
        assertThrows(ResponseStatusException.class, () -> uut.addListOfZoos(zoos));
        verify(mockZooRepo,times(1)).save(any(Zoo.class));
    }

    @Test
    void test_UpdateZoo_ValidRequest(){
        Zoo zoo = createAZoo();
        UUID id = zoo.getId();
        when(mockZooRepo.existsById(any())).thenReturn(true);
        uut.updateZooWithPut(zoo);
        //assertThrows(ResponseStatusException.class,() -> uut.updateZooWithPut(zoo));
        verify(mockZooRepo, times(1)).save(zoo);
    }

    @Test
    void test_UpdateZoo_InvalidRequest_NoId(){
        Zoo zoo = new Zoo();
        //when(mockZooRepo.existsById(any())).thenReturn(false);
        //uut.updateZooWithPut(zoo);
        assertThrows(ResponseStatusException.class,() -> uut.updateZooWithPut(null));
    }

    @Test
    void test_UpdateZoo_InvalidRequest_IdNotInDatabase(){
        Zoo zoo = createAZoo();
        when(mockZooRepo.existsById(any())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.updateZooWithPut(zoo));
    }

    @Test
    void test_DeleteZoo_ValidRequest_InDatabase() {
        Zoo zoo = createAZoo();
        when(mockZooRepo.existsById(any())).thenReturn(true);
        when(mockAnimalRepo.findAllAnimalsInAZoo(zoo.getId())).thenReturn(null);
        uut.removeZooById(zoo.getId());
        verify(mockZooRepo, times(1)).deleteById(zoo.getId());
    }

    @Test
    void test_DeleteZoo_ValidRequest_NotInDatabase() {
        Zoo zoo = createAZoo();
        when(mockZooRepo.existsById(any())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.removeZooById(zoo.getId()));
    }

    @Test
    void test_DeleteZoo_InvalidRequest_HasNoId() {
        assertThrows(ResponseStatusException.class,() -> uut.removeZooById(null));
    }

    @Test
    void test_DeleteZoo_InvalidRequest_ZooHasAnimals(){
        Zoo zoo = createAZoo();
        Animal animal = new Animal();
        animal.setZoo(zoo);
        assertThrows(ResponseStatusException.class,() -> uut.removeZooById(zoo.getId()));
    }

    @Test
    void test_UpdateZooName_ValidRequest_InDatabase() {
        Zoo zoo = createAZoo();
        String name = "NewName";
        when(mockZooRepo.existsById(zoo.getId())).thenReturn(true);
        when(mockZooRepo.findById(zoo.getId())).thenReturn(Optional.of(zoo));
        uut.updateZooByName(name, zoo.getId());
        verify(mockZooRepo, times(1)).save(any(Zoo.class));
    }

    @Test
    void test_UpdateZooName_ValidRequest_NotInDatabase() {
        Zoo zoo = createAZoo();
        String name = "NewName";
        when(mockZooRepo.existsById(zoo.getId())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.updateZooByName(name,zoo.getId()));
    }

    @Test
    void test_UpdateZooName_HasNoId() {
        String name = "newName";
        assertThrows(ResponseStatusException.class,() -> uut.updateZooByName(name,null));
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

    private Zoo createAZooNotInDataBase() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String json = "{\n" +
                "    \"id\": \"40ea5519-fcef-4272-b742-e01790c4453\",\n" +
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