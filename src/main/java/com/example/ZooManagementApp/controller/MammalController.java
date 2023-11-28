package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Mammal;
import com.example.ZooManagementApp.services.IMammalService;
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

    @GetMapping("/{mammalId}")
    public Mammal getMammalById(@PathVariable UUID mammalId) {
        return mammalService.findMammalById(mammalId);
    }

    @Operation(summary = "Add a Mammal", description = "Add a Mammal, returns a new Mammal")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mammal addMammal(@RequestBody @DateTimeFormat(pattern="dd-MM-yyyy") Mammal mammal) {
        return mammalService.addMammal(mammal);
    }
}
