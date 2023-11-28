package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Mammal;
import com.example.ZooManagementApp.services.IMammalService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SuppressWarnings("unused")
@Tag(name = "Mammal Api")
@RequestMapping("/mammals")
public class MammalController {

private final IMammalService mammalService;

    @Autowired
    public MammalController(IMammalService mammalService) {
        this.mammalService = mammalService;
    }

    @GetMapping
    public List<Mammal> getAllMammals() {
        return mammalService.findAllMammals();
    }
}
