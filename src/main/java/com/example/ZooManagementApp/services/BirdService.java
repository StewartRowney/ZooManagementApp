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
                .orElseThrow(() -> new EntityNotFoundException("Bird with id: "+ id+ " not found"));
    }


    @Override
    public Bird addNewBird(Bird bird) {
        return animalRepository.save(bird);
    }

    @Override
    public Bird updateBirdWithPut(Bird bird) {
        if (bird == null || bird.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bird to update must have an Id");
        }

        if (!animalRepository.existsById(bird.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Zoo to update does not exist");}
        return animalRepository.save(bird);
    }

    @Override
    public void removeBirdById(UUID id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bird to delete must have an Id");
        }
        if (!animalRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bird to Delete does not exist");
        }
        animalRepository.deleteById(id);
    }
}
