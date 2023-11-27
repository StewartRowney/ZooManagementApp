package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Insect;
import com.example.ZooManagementApp.services.IInsectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@SuppressWarnings("unused")
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
}
