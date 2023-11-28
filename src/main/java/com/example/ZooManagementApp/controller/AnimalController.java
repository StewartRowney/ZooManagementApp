package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Amphibian;
import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.services.IAnimalService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@SuppressWarnings("unused")
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

    @Operation(summary = "Add a list of animals to a zoo", description = "Add a list of animals to a zoo")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Animal> addListOfAnimals(@RequestBody @DateTimeFormat(pattern="dd-MM-yyyy") List<Animal> animals) {
        return animalService.addListOfAnimals(animals);
    }


}
