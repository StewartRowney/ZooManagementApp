package com.example.zoo.services;

import com.example.zoo.data.IAnimalRepository;
import com.example.zoo.data.ZooRepository;
import com.example.zoo.entities.Fish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class FishService implements IFishService {

    private final IAnimalRepository animalRepository;

    private final ZooRepository zooRepository;

    @Autowired
    public FishService(IAnimalRepository animalRepository, ZooRepository zooRepository) {
        this.animalRepository = animalRepository;
        this.zooRepository = zooRepository;
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
    public Fish addFish(Fish fish) {
        if (fish == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fish to add cannot be null");
        }
        else if (fish.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fish to add cannot contain an id");
        }
        else if (!zooRepository.existsById(fish.getZoo().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add a Fish to a zoo that doesn't exist");
        }
        return animalRepository.save(fish);
    }

    @Override
    public Fish updateFish(Fish fish) {
        if (fish == null || fish.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fish to update must have an Id");
        }

        if (!zooRepository.existsById(fish.getZoo().getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fish to update does not exist");}
        return animalRepository.save(fish);
    }

    @Override
    public void deleteFish(UUID id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fish to delete must have an Id");
        }

        if (!animalRepository.existsById(id)) { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Fish to update does not exist");}
        animalRepository.deleteById(id);
    }
}

