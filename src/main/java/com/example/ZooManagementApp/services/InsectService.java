package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.entities.Insect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InsectService implements IInsectService{

    private final IAnimalRepository animalRepository;

    @Autowired
    public InsectService(IAnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public List<Insect> findAllInsects() {
        return animalRepository.findAllInsects();
    }

    @Override
    public Insect findInsectById(UUID insectId) {
        if (insectId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insect id cannot be null for this operation");
        }
        return animalRepository.findInsectById(insectId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Insect with id: "+ insectId + " not found"));
    }
}
