package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Amphibian;
import com.example.ZooManagementApp.services.IAmphibianService;
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
@Tag(name = "Amphibian Api")
@RequestMapping("/amphibians")
public class AmphibianController {

    private final IAmphibianService amphibianService;

    @Autowired
    public AmphibianController(IAmphibianService amphibianService) {
        this.amphibianService = amphibianService;
    }

    @Operation(summary = "Get a list of all amphibians", description = "Returns a list of all amphibians")
    @GetMapping
    public List<Amphibian> getAllAmphibians() {
        return amphibianService.findAllAmphibians();
    }

    @Operation(summary = "Get amphibian by id", description = "Returns an amphibian by id")
    @GetMapping("findById/{amphibianId}")
    public Amphibian getAmphibian(@PathVariable UUID amphibianId) {
        return amphibianService.findAmphibianById(amphibianId);
    }

    @Operation(summary = "Add an amphibian", description = "Add an amphibian")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Amphibian addAmphibian(@RequestBody @DateTimeFormat(pattern="dd-MM-yyyy") Amphibian amphibian) {
        return amphibianService.addAmphibian(amphibian);
    }

    @Operation(summary = "Update an amphibian", description = "Update an amphibian")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Amphibian updateAmphibian(@RequestBody @DateTimeFormat(pattern="dd-MM-yyyy") Amphibian amphibian) {
        return amphibianService.updateAmphibian(amphibian);
    }

    @Operation(summary = "Delete an amphibian by id", description = "Delete an amphibian by id")
    @DeleteMapping("findById/{amphibianId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAmphibian(@PathVariable UUID amphibianId) {
        amphibianService.deleteAmphibian(amphibianId);
    }


}
