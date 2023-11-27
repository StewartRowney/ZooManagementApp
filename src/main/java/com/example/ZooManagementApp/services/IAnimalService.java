package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.entities.Animal;

import java.util.List;

public interface IAnimalService {
    List<Animal> findAllAnimals();
}
