package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.entities.Fish;
import com.example.ZooManagementApp.services.IFishService;
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
@Tag(name = "Fish Api")
@RequestMapping("/fish")
public class FishController {

    private final IFishService fishService;

    @Autowired
    public FishController(IFishService fishService) {
        this.fishService = fishService;
    }

    @GetMapping
    public List<Fish> getAllFish() {
        return fishService.findAllFish();
    }

    @GetMapping("/{id}")
    public Fish getFishById(@PathVariable UUID id){
        return fishService.findFishById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fish putAFish(@RequestBody Fish fish){
        return fishService.updateFishWithPut(fish);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteZooById(@PathVariable UUID id){
        fishService.removeFishById(id);
    }

    @Operation(summary = "Add a Mammal", description = "Add a Mammal, returns a new Mammal")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fish addFish(@RequestBody @DateTimeFormat(pattern="dd-MM-yyyy") Fish fish) {
        return fishService.addFish(fish);
    }
}


