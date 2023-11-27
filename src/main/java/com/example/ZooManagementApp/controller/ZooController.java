package com.example.ZooManagementApp.controller;
import com.example.ZooManagementApp.entities.Zoo;
import com.example.ZooManagementApp.services.IZooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@SuppressWarnings("unused")
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

    @GetMapping("/zooById/{id}")
    public Zoo getZooById(@PathVariable UUID id){
        return service.findZooById(id);
    }

    @GetMapping("/zooByName/{name}")
    public Zoo getZooByName(@PathVariable String name){
        return service.findZooByName(name);
    }
}
