package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.entities.Mammal;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MammalService implements IMammalService{

    private final IAnimalRepository animalRepository;

    @Autowired
    public MammalService(IAnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public List<Mammal> findAllMammals() {
        return animalRepository.findAllMammals();
    }

    @Override
    public Mammal findMammalById(UUID mammalId) {
        if (mammalId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mammal id cannot be null for this operation");
        }
        return animalRepository.findMammalById(mammalId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mammal with id: "+ mammalId + " not found"));
    }
}
