package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.entities.Fish;
import com.example.ZooManagementApp.entities.Mammal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class FishService implements IFishService {

    private final IAnimalRepository animalRepository;

    @Autowired
    public FishService(IAnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public List<Fish> findAllFish() {
        return animalRepository.findAllFish();
    }

    @Override
    public Fish findFishById(UUID fishId) {
        if (fishId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fish id cannot be null for this operation");
        }
        return animalRepository.findFishById(fishId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Fish with id: "+ fishId + " not found"));
    }

    @Override
    public Fish updateFishWithPut(Fish fish) {
        if (fish == null || fish.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fish to update must have an Id");
        }

        if (!animalRepository.existsById(fish.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fish to update does not exist");}
        return animalRepository.save(fish);
    }

    @Override
    public void removeFishById(UUID id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fish to delete must have an Id");
        }

        if (!animalRepository.existsById(id)) { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fish to update does not exist");}
        animalRepository.deleteById(id);
    }

    @Override
    public Fish addFish(Fish fish) {
        if (fish == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mammal to add cannot be null");
        }
        else if (fish.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mammal to add cannot contain an id");
        }
        else if (!animalRepository.existsById(fish.getZoo().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add Mammal to a zoo that doesn't exist");
        }
        return animalRepository.save(fish);
    }
}

