package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Insect;
import com.example.ZooManagementApp.entities.Reptile;
import com.example.ZooManagementApp.services.IInsectService;
import com.example.ZooManagementApp.services.IReptileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SuppressWarnings("unused")
@RequestMapping("/reptiles")
public class ReptileController {

    private final IReptileService reptileService;

    @Autowired
    public ReptileController(IReptileService reptileService) {
        this.reptileService = reptileService;
    }

    @GetMapping
    public List<Reptile> getAllReptiles() {
        return reptileService.findAllReptiles();
    }
}
