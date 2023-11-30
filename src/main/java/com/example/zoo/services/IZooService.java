package com.example.zoo.services;

import com.example.zoo.entities.Zoo;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.UUID;

public interface IZooService {
    List<Zoo> findAllZoos();

    Zoo findZooById(UUID id);

    Zoo findZooByName(String name);

    Zoo addNewZoo(Zoo zoo);

    Zoo updateZooWithPut(Zoo zoo);

    Zoo updateZooByName(String name, UUID id);

    void removeZooById(UUID id);

    List<Zoo> addListOfZoos(List<Zoo> zoos) throws JsonProcessingException;
}
