package com.example.zoo.controller;

import com.example.zoo.entities.Zoo;
import com.example.zoo.services.IZooService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ZooControllerNoSpringTest {
    IZooService mockZooService= Mockito.mock(IZooService.class);
    ZooController uut= new ZooController(mockZooService);;

    private final Zoo zoo = new Zoo();
    private final UUID id= UUID.randomUUID();
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void test_getAllZoos() {
        uut.getAllZoos();
        verify(mockZooService, times(1)).findAllZoos();
    }

    @Test
    void test_findZooById() {
        when(mockZooService.findZooById(id)).thenReturn((new Zoo()));
        uut.getZooById(id);
        verify(mockZooService,times(1)).findZooById(id);
    }
    @Test
    void test_findZooByName() {
        String name= "someZoo";
        when(mockZooService.findZooByName(anyString())).thenReturn((new Zoo()));
        uut.getZooByName(name);
        verify(mockZooService,times(1)).findZooByName(name);
    }

    @Test
    void test_AddNewZoo(){
        uut.postNewZoo(zoo);
        verify(mockZooService,times(1)).addNewZoo(zoo);
    }

    @Test
    void test_AddAListOfZoos() throws JsonProcessingException {
        List<Zoo> zoos = new ArrayList<>();
        zoos.add(zoo);
        uut.addAListOfZoos(zoos);
        verify(mockZooService,times(zoos.toArray().length)).addListOfZoos(zoos);
    }

    @Test
    void test_UpdateZoo(){
        uut.putAZoo(zoo);
        verify(mockZooService,times(1)).updateZoo(zoo);
    }

    @Test
    void test_updateZooName(){
        uut.patchZooName(zoo.getId(),"newName");
        verify(mockZooService,times(1)).updateZooName("newName", zoo.getId());
    }

    @Test
    void test_DeleteAZoo(){
        uut.deleteZooById(zoo.getId());
        verify(mockZooService,times(1)).removeZooById(zoo.getId());
    }



}