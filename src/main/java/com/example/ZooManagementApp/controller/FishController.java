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
    @Operation(summary = "Get a fish by id", description = "Returns a fish by id")
    @GetMapping
    public List<Fish> getAllFish() {
        return fishService.findAllFish();
    }
    @Operation(summary = "Get a list of all fishes", description = "Returns a list of all fishes")
    @GetMapping("/{id}")
    public Fish getFishById(@PathVariable UUID id){
        return fishService.findFishById(id);
    }
    @Operation(summary = "Update a fish", description = "Update a fish")
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fish putAFish(@RequestBody Fish fish){
        return fishService.updateFishWithPut(fish);
    }

    @Operation(summary = "Delete a fish by id", description = "Delete a fish by id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteZooById(@PathVariable UUID id){
        fishService.removeFishById(id);
    }

    @Operation(summary = "Add a fish", description = "Add a fish")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fish addFish(@RequestBody @DateTimeFormat(pattern="dd-MM-yyyy") Fish fish) {
        return fishService.addFish(fish);
    }
}


