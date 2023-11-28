package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Insect;
import com.example.ZooManagementApp.services.IInsectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<Insect> getAllInsects() {
        return insectService.findAllInsects();
    }

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
}
