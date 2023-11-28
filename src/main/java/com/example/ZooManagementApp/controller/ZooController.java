package com.example.ZooManagementApp.controller;
import com.example.ZooManagementApp.entities.Zoo;
import com.example.ZooManagementApp.services.IZooService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@SuppressWarnings("UnusedReturnValue")
@Tag(name = "Zoo Api")
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

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Zoo putAZoo(@RequestBody Zoo zoo){
        return service.updateZooWithPut(zoo);
    }

    @PatchMapping("/editName/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Zoo patchZooName(@PathVariable UUID id, @RequestBody String name){
        return service.updateZooByName(name, id);
    }

    @DeleteMapping("/deleteZoo/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteZooById(@PathVariable UUID id){

        service.removeZooById(id);
    }


}
