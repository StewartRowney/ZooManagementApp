package com.example.zoo.controller;

import com.example.zoo.entities.Fish;
import com.example.zoo.services.IFishService;
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
@Tag(name = "Fish Api")
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/fish")
public class FishController {

    private final IFishService fishService;

    @Autowired
    public FishController(IFishService fishService) {
        this.fishService = fishService;
    }

    @Operation(summary = "Get a list of all fishes", description = "Returns a list of all fishes")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Fish> getAllFish() {
        return fishService.findAllFish();
    }

    @Operation(summary = "Get a fish by id", description = "return a fish by id")
    @GetMapping("/{fishId}")
    @ResponseStatus(HttpStatus.OK)
    public Fish getFish(@PathVariable UUID fishId){
        return fishService.findFishById(fishId);
    }

    @Operation(summary = "Add a Fish", description = "Add a Fish, returns a new Fish")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fish addFish(@RequestBody @DateTimeFormat(pattern="yyyy-MM-dd") Fish fish) {
        return fishService.addFish(fish);
    }

    @Operation(summary = "Update a Fish", description = "Update a Fish")
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fish updateFish(@RequestBody Fish fish){
        return fishService.updateFish(fish);
    }

    @Operation(summary = "Delete a Fish by id", description = "Delete a Fish by id")
    @DeleteMapping("/{fishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFish(@PathVariable UUID fishId){
        fishService.deleteFish(fishId);
    }
}


