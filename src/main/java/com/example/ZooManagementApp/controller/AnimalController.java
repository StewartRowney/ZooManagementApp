package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Amphibian;
import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.entities.Reptile;
import com.example.ZooManagementApp.services.IAnimalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@SuppressWarnings({"unused", "UnusedReturnValue"})
@Tag(name = "Animal Api")
@RequestMapping("/animals")
public class AnimalController {

    private final IAnimalService animalService;

    @Autowired
    public AnimalController(IAnimalService animalService) {
        this.animalService = animalService;
    }

    @Operation(summary = "Get a list of all animals", description = "Returns a list of all animals")
    @GetMapping
    public List<Animal> getAllAnimals() {
        return animalService.findAllAnimals();
    }

    @Operation(summary = "Get a animal by id", description = "return a animal by id")
    @GetMapping("/findById/{id}")
    public Animal getAnimalById(@PathVariable UUID id){
        return animalService.findAnimalById(id);
    }

    @Operation(summary = "Find a list of animals in a zoo", description = "Find a list of animals in a zoo")
    @PostMapping("/findByIds")
    public List<Animal> getAnimalListById(@RequestBody List<UUID> idList){
        return animalService.findAnimalListById(idList);
    }

//    @Operation(summary = "Add a list of animals to a zoo", description = "Add a list of animals to a zoo")
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public List<Animal> addListOfAnimals(@RequestBody @DateTimeFormat(pattern="dd-MM-yyyy") List<Animal> animals) {
//        return animalService.addListOfAnimals(animals);
//    }


}
