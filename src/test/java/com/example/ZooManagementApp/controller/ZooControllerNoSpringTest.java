package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Zoo;
import com.example.ZooManagementApp.services.IZooService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ZooControllerNoSpringTest {
    ZooController uut;
    IZooService mockZooService;

    private final Zoo zoo = new Zoo();
    private final UUID zooId= UUID.randomUUID();
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());


    @BeforeEach
    void initialiseFields(){
        mockZooService = Mockito.mock(IZooService.class);
        uut = new ZooController(mockZooService);

    }
    @Test
    void getAllZoos() {
        uut.getAllZoos();
        verify(mockZooService, times(1)).findAllZoos();
    }

    @Test
    void verifyIfRepositoryInvokesFindById() {
        UUID id= UUID.randomUUID();
        when(mockZooService.findZooById(id)).thenReturn((new Zoo()));
        uut.getZooById(id);
        verify(mockZooService,times(1)).findZooById(id);
    }
    @Test
    void verifyIfRepositoryInvokesFindByName() {
        String name= "someZoo";
        when(mockZooService.findZooByName(anyString())).thenReturn((new Zoo()));
        uut.getZooByName(name);
        verify(mockZooService,times(1)).findZooByName(name);
    }

    @Test
    void test_AddNewZoo(){
        Zoo zoo = new Zoo();
        uut.postNewZoo(zoo);
        verify(mockZooService,times(1)).addNewZoo(zoo);
    }

    @Test
    void test_AddAListOfZoos() throws JsonProcessingException {
        List<Zoo> zoos = new ArrayList<>();
        Zoo zoo1 = new Zoo();
        zoos.add(zoo1);
        uut.addAListOfZoos(zoos);
        verify(mockZooService,times(zoos.toArray().length)).addListOfZoos(zoos);
    }

    @Test
    void test_UpdateZoo(){
        Zoo zoo = new Zoo();
        uut.putAZoo(zoo);
        verify(mockZooService,times(1)).updateZooWithPut(zoo);
    }

    @Test
    void test_updateZooName(){
        Zoo zoo = new Zoo();
        uut.patchZooName(zoo.getId(),"newName");
        verify(mockZooService,times(1)).updateZooByName("newName", zoo.getId());
    }

    @Test
    void test_DeleteAZoo(){
        Zoo zoo = new Zoo();
        uut.deleteZooById(zoo.getId());
        verify(mockZooService,times(1)).removeZooById(zoo.getId());
    }



}