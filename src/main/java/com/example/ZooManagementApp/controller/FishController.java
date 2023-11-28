package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.entities.Fish;
import com.example.ZooManagementApp.services.IFishService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/findById/{id}")
    public Fish getFishById(@PathVariable UUID id){
        return fishService.findFishById(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Fish putAFish(@RequestBody Fish fish){
        return fishService.updateFishWithPut(fish);
    }
}


