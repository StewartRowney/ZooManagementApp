package com.example.zoo.controller;

import com.example.zoo.entities.Mammal;
import com.example.zoo.services.IMammalService;
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
@Tag(name = "Mammal Api")
@RequestMapping("/mammals")
@CrossOrigin(origins = "http://localhost:3000")
public class MammalController {

private final IMammalService mammalService;

    @Autowired
    public MammalController(IMammalService mammalService) {
        this.mammalService = mammalService;
    }
    @Operation(summary = "Get a list of all mammals", description = "Returns a list of all mammals")
    @GetMapping
    public List<Mammal> getAllMammals() {
        return mammalService.findAllMammals();
    }
    @Operation(summary = "Get mammal by id", description = "Returns a mammal by id")
    @GetMapping("/{mammalId}")
    public Mammal getMammal(@PathVariable UUID mammalId) {
        return mammalService.findMammalById(mammalId);
    }

    @Operation(summary = "Add a Mammal", description = "Add a Mammal, returns a new Mammal")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mammal addMammal(@RequestBody @DateTimeFormat(pattern="yyyy-MM-dd") Mammal mammal) {
        return mammalService.addMammal(mammal);
    }

    @Operation(summary = "Update a Mammal", description = "Update a Mammal, returns the updated Mammal")
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mammal updateMammal(@RequestBody @DateTimeFormat(pattern="yyyy-MM-dd") Mammal mammal) {
        return mammalService.updateMammal(mammal);
    }

    @Operation(summary = "Delete a Mammal by Id", description = "Delete a Mammal by Id, returns no content")
    @DeleteMapping("/{mammalId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMammal(@PathVariable UUID mammalId) {
        mammalService.deleteMammal(mammalId);
    }
}
