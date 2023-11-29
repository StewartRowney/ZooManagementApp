package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.services.IAnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@SuppressWarnings({"unused", "UnusedReturnValue"})
@RequestMapping("/animals")
public class AnimalController {

    private final IAnimalService animalService;

    @Autowired
    public AnimalController(IAnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping
    public List<Animal> getAllAnimals() {
        return animalService.findAllAnimals();
    }

    @GetMapping("/findById/{id}")
    public Animal getAnimalById(@PathVariable UUID id){
        return animalService.findAnimalById(id);
    }



}
