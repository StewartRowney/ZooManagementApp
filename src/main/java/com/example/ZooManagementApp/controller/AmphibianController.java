package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Amphibian;
import com.example.ZooManagementApp.services.IAmphibianService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

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

    @GetMapping
    public List<Amphibian> getAllAmphibians() {
        return amphibianService.getAllAmphibians();
    }
}
