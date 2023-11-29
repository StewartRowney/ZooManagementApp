package com.example.ZooManagementApp.controller;

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
@SuppressWarnings({"unused", "UnusedReturnValue"})
@Tag(name = "Fish Api")
@RequestMapping("/fish")
public class FishController {

    private final IFishService fishService;

    @Autowired
    public FishController(IFishService fishService) {
        this.fishService = fishService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Fish> getAllFish() {
        return fishService.findAllFish();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Fish getFish(@PathVariable UUID id){
        return fishService.findFishById(id);
    }

    @Operation(summary = "Add a Fish", description = "Add a Fish, returns a new Fish")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fish addFish(@RequestBody @DateTimeFormat(pattern="dd-MM-yyyy") Fish fish) {
        return fishService.addFish(fish);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fish updateFish(@RequestBody Fish fish){
        return fishService.updateFish(fish);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFish(@PathVariable UUID id){
        fishService.deleteFish(id);
    }
}


