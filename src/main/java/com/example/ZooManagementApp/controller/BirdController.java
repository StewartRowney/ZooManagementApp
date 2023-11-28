package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Bird;
import com.example.ZooManagementApp.services.IBirdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@SuppressWarnings("unused")
@Tag(name = "Bird Api")
@RequestMapping("/birds")
public class BirdController {

    private final IBirdService service;

    @Autowired
    public BirdController(IBirdService birdService) {
        this.service = birdService;
    }

    @Operation(summary = "Get a list of all birds", description = "Returns a list of all birds")
    @GetMapping
    public List<Bird> getAllBirds() {
        return service.findAllBirds();
    }
    @Operation(summary = "Get Bird by id", description = "Returns a bird by id")
    @GetMapping("/findById/{id}")
    public Bird getBirdById(@PathVariable UUID id){
        return service.findBirdById(id);
    }
    @Operation(summary = "Add a bird", description = "Add a bird")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bird postNewBird(@RequestBody @DateTimeFormat(pattern="dd-MM-yyyy") Bird bird){
        return service.addNewBird(bird);
    }
    @Operation(summary = "Update a bird", description = "Update a bird")
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bird putABird(@RequestBody @DateTimeFormat(pattern="dd-MM-yyyy") Bird bird){
        return service.updateBirdWithPut(bird);
    }
    @Operation(summary = "Delete a bird by id", description = "Delete a bird by id")
    @DeleteMapping("/deleteBird/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBirdById(@PathVariable UUID id){
        service.removeBirdById(id);
    }
}
