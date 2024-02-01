package com.example.zoo.controller;

import com.example.zoo.entities.Bird;
import com.example.zoo.services.IBirdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@SuppressWarnings({"unused", "UnusedReturnValue"})
@Tag(name = "Bird Api")
@CrossOrigin(origins = "http://localhost:3000")

@RequestMapping("/birds")
public class BirdController {

    private final IBirdService service;

    @Autowired
    public BirdController(IBirdService birdService) {
        this.service = birdService;
    }

    @Operation(summary = "Get a list of all birds", description = "Returns a list of all birds")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Bird> getAllBirds() {
        return service.findAllBirds();
    }

    @Operation(summary = "Get Bird by id", description = "Returns a bird by id")
    @GetMapping("/{birdId}")
    @ResponseStatus(HttpStatus.OK)
    public Bird getBird(@PathVariable UUID birdId){
        return service.findBirdById(birdId);
    }

    @Operation(summary = "Add a bird", description = "Add a bird, returns new bird")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bird addBird(@RequestBody @DateTimeFormat(pattern="yyyy-MM-dd") Bird bird){
        return service.addBird(bird);
    }

    @Operation(summary = "Update a bird", description = "Update a bird, returns updated bird")
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bird updateBird(@RequestBody @DateTimeFormat(pattern="yyyy-MM-dd") Bird bird){
        return service.updateBird(bird);
    }

    @Operation(summary = "Delete a bird by id", description = "Delete a bird by id")
    @DeleteMapping("/{birdId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBird(@PathVariable UUID birdId){
        service.deleteBird(birdId);
    }
}
