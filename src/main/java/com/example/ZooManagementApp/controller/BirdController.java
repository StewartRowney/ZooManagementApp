package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Bird;
import com.example.ZooManagementApp.entities.Fish;
import com.example.ZooManagementApp.services.IBirdService;
import com.example.ZooManagementApp.services.IFishService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@SuppressWarnings("unused")
@Tag(name = "Bird Api")
@RequestMapping("/birds")
public class BirdController {

    private final IBirdService birdService;

    @Autowired
    public BirdController(IBirdService birdService) {
        this.birdService = birdService;
    }

    @GetMapping
    public List<Bird> getAllBirds() {
        return birdService.findAllBirds();
    }
}
