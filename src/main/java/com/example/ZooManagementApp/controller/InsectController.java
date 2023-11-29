package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Insect;
import com.example.ZooManagementApp.entities.Mammal;
import com.example.ZooManagementApp.services.IInsectService;
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
@Tag(name = "Insect Api")
@RequestMapping("/insects")
public class InsectController {

    private final IInsectService insectService;

    @Autowired
    public InsectController(IInsectService insectService) {
        this.insectService = insectService;
    }
    @Operation(summary = "Get all insects", description = "Returns a list of all insects")
    @GetMapping
    public List<Insect> getAllInsects() {
        return insectService.findAllInsects();
    }

    @Operation(summary = "Get an insect by ID", description = "Returns an insect by ID")
    @GetMapping("/{insectId}")
    public Insect getInsectById(@PathVariable UUID insectId) {
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
    public Insect updateInsect(@RequestBody @DateTimeFormat(pattern="dd-MM-yyyy") Insect insect) {
        return insectService.updateInsect(insect);
    }

    @Operation(summary = "Delete an Insect by Id", description = "Delete an Insect by Id, returns no content")
    @DeleteMapping("/{insectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInsectById(@PathVariable UUID insectId) {
        insectService.deleteInsectById(insectId);
    }
}
