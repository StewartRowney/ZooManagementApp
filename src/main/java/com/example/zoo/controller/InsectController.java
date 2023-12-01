package com.example.zoo.controller;

import com.example.zoo.entities.Insect;
import com.example.zoo.services.IInsectService;
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
@Tag(name = "Insect Api")
@RequestMapping("/insects")
public class InsectController {

    private final IInsectService insectService;

    @Autowired
    public InsectController(IInsectService insectService) {
        this.insectService = insectService;
    }

    @Operation(summary = "Get a list of all insects", description = "Returns a list of all insects")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Insect> getAllInsects() {
        return insectService.findAllInsects();
    }

    @Operation(summary = "Get a insect by id", description = "Returns a insect by id")
    @GetMapping("/{insectId}")
    @ResponseStatus(HttpStatus.OK)
    public Insect getInsect(@PathVariable UUID insectId) {
        return insectService.findInsectById(insectId);
    }

    @Operation(summary = "Add an Insect", description = "Add an Insect, returns a new Insect")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Insect addInsect(@RequestBody Insect insect) {
        return insectService.addInsect(insect);
    }

    @Operation(summary = "Update an Insect", description = "Update an Insect, returns the updated Insect")
    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Insect updateInsect(@RequestBody @DateTimeFormat(pattern="yyyy-MM-dd") Insect insect) {
        return insectService.updateInsect(insect);
    }

    @Operation(summary = "Delete an Insect by Id", description = "Delete an Insect by Id, returns no content")
    @DeleteMapping("/{insectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInsect(@PathVariable UUID insectId) {
        insectService.deleteInsect(insectId);
    }
}
