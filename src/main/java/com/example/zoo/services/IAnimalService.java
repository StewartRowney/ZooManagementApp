package com.example.zoo.services;

import com.example.zoo.entities.Animal;

import java.util.List;
import java.util.UUID;

public interface IAnimalService {

    List<Animal> findAllAnimals();
    Animal findAnimalById(UUID id);
   // Animal addListOfAnimals(List<Animal> animals);
    List<Animal> findAnimalListById(List<UUID> idList);
}
