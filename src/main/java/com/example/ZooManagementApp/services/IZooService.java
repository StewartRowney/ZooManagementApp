package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.entities.Zoo;

import java.util.List;
import java.util.UUID;

public interface IZooService {
    List<Zoo> findAllZoos();

    Zoo findZooById(UUID id);

    Zoo findZooByName(String name);

    Zoo addNewZoo(Zoo zoo);
}
