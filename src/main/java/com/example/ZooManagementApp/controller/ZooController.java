package com.example.ZooManagementApp.controller;
import com.example.ZooManagementApp.entities.Zoo;
import com.example.ZooManagementApp.services.IZooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@SuppressWarnings("UnusedReturnValue")
@RequestMapping("/zoos")
public class ZooController {
    IZooService service;

    @Autowired
    ZooController (IZooService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<Zoo> getAllZoos() {
        return service.findAllZoos();
    }

    @GetMapping("/findById/{id}")
    public Zoo getZooById(@PathVariable UUID id){
        return service.findZooById(id);
    }

    @GetMapping("/findByName/{name}")
    public Zoo getZooByName(@PathVariable String name){
        return service.findZooByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Zoo postNewZoo(@RequestBody Zoo zoo){
        return service.addNewZoo(zoo);
    }

}
