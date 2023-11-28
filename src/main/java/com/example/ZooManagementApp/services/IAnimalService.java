package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.entities.Animal;

import java.util.List;
import java.util.UUID;

public interface IAnimalService {

    List<Animal> findAllAnimals();
    Animal findAnimalById(UUID id);
    Animal addListOfAnimals(List<Animal> animals);

}
