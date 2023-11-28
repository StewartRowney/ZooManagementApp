package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.entities.Bird;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class BirdService implements IBirdService {

    private final IAnimalRepository animalRepository;

    @Autowired
    public BirdService(IAnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public List<Bird> findAllBirds() {
        return animalRepository.findAllBirds();
    }

    @Override
    public Bird findBirdById(UUID id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bird to update must have an Id");
        }
        return animalRepository.findBirdById(id)
                .orElseThrow(() -> new EntityNotFoundException("Zoo with id: "+ id+ " not found"));
    }

    @Override
    public Bird addNewBird(Bird bird) {
        return null;
    }

    @Override
    public Bird updateBirdWithPut(Bird bird) {
        return null;
    }

    @Override
    public void removeBirdById(UUID id) {

    }
}
