package com.example.zoo.controller;

import com.example.zoo.entities.Zoo;
import com.example.zoo.services.IZooService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@SuppressWarnings({"unused", "UnusedReturnValue"})
@Tag(name = "Zoo Api")
@RequestMapping("/zoos")
public class ZooController {
    IZooService service;

    @Autowired
    ZooController (IZooService service) {
        this.service = service;
    }

    @Operation(summary = "Get a list of all zoos", description = "Returns a list of all zoos")
    @GetMapping("")
    public List<Zoo> getAllZoos() {
        return service.findAllZoos();
    }

    @Operation(summary = "Get a zoo by id", description = "Returns a zoo by id")
    @GetMapping("/findById/{id}")
    public Zoo getZooById(@PathVariable UUID id){
        return service.findZooById(id);
    }
    @Operation(summary = "Get a zoo by name", description = "Returns a zoo by name")
    @GetMapping("/findByName/{name}")
    public Zoo getZooByName(@PathVariable String name){
        return service.findZooByName(name);
    }

    @Operation(summary = "Add a zoo", description = "Add a zoo")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Zoo postNewZoo(@RequestBody @DateTimeFormat(pattern="dd-MM-yyyy") Zoo zoo ){
        return service.addNewZoo(zoo);
    }
    @Operation(summary = "Update a zoo", description = "Update a zoo")
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Zoo putAZoo(@RequestBody @DateTimeFormat(pattern="dd-MM-yyyy") Zoo zoo){
        return service.updateZooWithPut(zoo);
    }
    @Operation(summary = "Update a zoo's name", description = "Update a zoo's name")
    @PatchMapping("/editName/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Zoo patchZooName(@PathVariable @DateTimeFormat(pattern="dd-MM-yyyy") UUID id, @RequestBody String name){
        return service.updateZooByName(name, id);
    }
    @Operation(summary = "Delete a zoo by id", description = "Delete a zoo by id")
    @DeleteMapping("/deleteZoo/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteZooById(@PathVariable UUID id){
        service.removeZooById(id);
    }
    @Operation(summary = "Add a list of Zoos", description = "Add zoos in bulk through a list")
    @PostMapping("/addZoos")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Zoo> addAListOfZoos(@RequestBody @DateTimeFormat(pattern="dd-MM-yyyy") List<Zoo> zoos) throws JsonProcessingException {
        return service.addListOfZoos(zoos);}
}
