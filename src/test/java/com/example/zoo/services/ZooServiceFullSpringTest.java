package com.example.zoo.services;

import com.example.zoo.data.IAnimalRepository;
import com.example.zoo.data.ZooRepository;
import com.example.zoo.entities.Animal;
import com.example.zoo.entities.Zoo;
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

    private final Zoo zoo = new Zoo();
    private final UUID id= UUID.randomUUID();


    @Test
    void test_FindAllZoos_ValidRequest() {
        uut.findAllZoos();
        verify(mockZooRepo,times(1)).findAll();
    }

    @Test
    void test_findZooById_ValidRequest() {
        when(mockZooRepo.findById(id)).thenReturn(Optional.of(new Zoo()));
        uut.findZooById(id);
        verify(mockZooRepo,times(1)).findById(id);
    }

    @Test
    void test_findZooById_nullId(){
        assertThrows(ResponseStatusException.class,() -> uut.findZooById(null));
    }

    @Test
    void test_ZooFindByName_ValidRequest() {
        String name= "someZoo";
        when(mockZooRepo.findByName(anyString())).thenReturn(Optional.of(new Zoo()));
        uut.findZooByName(name);
        verify(mockZooRepo,times(1)).findByName(name);
    }

    @Test
    void test_AddNewZoo_ValidRequest() {
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
    void test_AddNewZoo_InvalidRequest_ZooIsNull(){
        assertThrows(ResponseStatusException.class,() -> uut.addNewZoo(null));
    }

    @Test
    void test_AddListOfZoos_ValidRequest() throws JsonProcessingException {
        List<Zoo> zoos = new ArrayList<>();
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
        when(mockZooRepo.existsById(any())).thenReturn(true);
        uut.updateZoo(zoo);
        verify(mockZooRepo, times(1)).save(zoo);
    }

    @Test
    void test_UpdateZoo_InvalidRequest_NoId(){
        assertThrows(ResponseStatusException.class,() -> uut.updateZoo(null));
    }

    @Test
    void test_UpdateZoo_InvalidRequest_IdNotInDatabase(){
        Zoo zoo = createAZoo();
        when(mockZooRepo.existsById(any())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.updateZoo(zoo));
    }

    @Test
    void test_DeleteZoo_ValidRequest_InDatabase() {
        Zoo zoo = createAZoo();
        when(mockZooRepo.existsById(any())).thenReturn(true);
        uut.removeZooById(zoo.getId());
        verify(mockZooRepo, times(1)).deleteById(zoo.getId());
    }

    @Test
    void test_DeleteZoo_ValidRequest_NotInDatabase() {
        when(mockZooRepo.existsById(any())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.removeZooById(id));
    }

    @Test
    void test_DeleteZoo_InvalidRequest_HasNoId() {
        assertThrows(ResponseStatusException.class,() -> uut.removeZooById(null));
    }

    @Test
    void test_DeleteZoo_InvalidRequest_ZooHasAnimals(){
        List<Animal> animals = new ArrayList<>();
        Animal animal = new Animal();
        animals.add(animal);
        when(mockAnimalRepo.findAllAnimalsInAZoo(String.valueOf(id))).thenReturn(animals);
        assertThrows(ResponseStatusException.class,() -> uut.removeZooById(id));
    }

    @Test
    void test_UpdateZooName_ValidRequest_InDatabase() {
        Zoo zoo = createAZoo();
        String name = "NewName";
        when(mockZooRepo.existsById(zoo.getId())).thenReturn(true);
        when(mockZooRepo.findById(zoo.getId())).thenReturn(Optional.of(zoo));
        uut.updateZooName(name, zoo.getId());
        verify(mockZooRepo, times(1)).save(any(Zoo.class));
    }

    @Test
    void test_UpdateZooName_ValidRequest_NotInDatabase() {
        Zoo zoo = createAZoo();
        String name = "NewName";
        when(mockZooRepo.existsById(zoo.getId())).thenReturn(false);
        assertThrows(ResponseStatusException.class,() -> uut.updateZooName(name,id));
    }

    @Test
    void test_UpdateZooName_HasNoId() {
        String name = "newName";
        assertThrows(ResponseStatusException.class,() -> uut.updateZooName(name,null));
    }

    private Zoo createAZoo() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String json = """
                {
                    "id": "40ea5519-fcef-4272-b742-e01790ca04c3",
                    "name": "Chester Zoo",
                    "location": "Upton-by-Chester, Cheshire, England",
                    "capacity": 27000,
                    "price": 19,
                    "dateOpened": "1931-06-10"
                  }""";
        try{
            return mapper.readValue(json, Zoo.class);
        } catch (Exception e) {
            return new Zoo();
        }
    }
}