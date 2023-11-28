package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Bird;
import com.example.ZooManagementApp.services.IBirdService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<Bird> getAllBirds() {
        return service.findAllBirds();
    }

    @GetMapping("/findById/{id}")
    public Bird getBirdById(@PathVariable UUID id){
        return service.findBirdById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bird postNewBird(@RequestBody Bird bird){
        return service.addNewBird(bird);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bird putABird(@RequestBody Bird bird){
        return service.updateBirdWithPut(bird);
    }

    @DeleteMapping("/deleteBird/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBirdById(@PathVariable UUID id){

        service.removeBirdById(id);
    }
}
