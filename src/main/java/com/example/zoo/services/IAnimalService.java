package com.example.zoo.services;

import com.example.zoo.entities.Animal;

import java.util.List;
import java.util.UUID;

public interface IAnimalService {

    List<Animal> findAllAnimals();
    Animal findAnimalById(UUID id);
    List<Animal> findAnimalListById(List<UUID> idList);
    void deleteAnimalsByIds(List<UUID> idList);
    List<Animal> findAnimalListByZooId(UUID zooId);
}
