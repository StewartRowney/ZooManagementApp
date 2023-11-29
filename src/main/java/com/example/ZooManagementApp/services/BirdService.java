package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.data.ZooRepository;
import com.example.ZooManagementApp.entities.Bird;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@SuppressWarnings({"unused"})
public class BirdService implements IBirdService {

    private final IAnimalRepository animalRepository;
    private final ZooRepository zooRepository;

    @Autowired
    public BirdService(IAnimalRepository animalRepository, ZooRepository zooRepository) {
        this.animalRepository = animalRepository;
        this.zooRepository = zooRepository;
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
                .orElseThrow(() -> new EntityNotFoundException("Bird with id: "+ id + " not found"));
    }
    @Override
    public Bird addBird(Bird bird) {
        if (bird == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bird to add cannot be null");
        }
        else if (bird.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bird to add cannot contain an id");
        }
        else if (!zooRepository.existsById(bird.getZoo().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add Bird to a zoo that doesn't exist");
        }
        return animalRepository.save(bird);
    }

    @Override
    public Bird updateBird(Bird bird) {
        if (bird == null || bird.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bird to update must have an Id");
        }

        if (!animalRepository.existsById(bird.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bird to update does not exist");
        }
        return animalRepository.save(bird);
    }

    @Override
    public void deleteBird(UUID id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bird to delete must have an Id");
        }
        if (!animalRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bird to Delete does not exist");
        }
        animalRepository.deleteById(id);
    }
}
