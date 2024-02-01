package com.example.zoo.services;

import com.example.zoo.data.IAnimalRepository;
import com.example.zoo.data.ZooRepository;
import com.example.zoo.entities.Mammal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@SuppressWarnings({"unused"})
public class MammalService implements IMammalService{

    private final IAnimalRepository animalRepository;
    private final ZooRepository zooRepository;

    @Autowired
    public MammalService(IAnimalRepository animalRepository, ZooRepository zooRepository) {
        this.animalRepository = animalRepository;
        this.zooRepository = zooRepository;
    }

    public List<Mammal> findAllMammals() {
        return animalRepository.findAllMammals();
    }

    @Override
    public Mammal findMammalById(UUID mammalId) {
        if (mammalId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mammal id cannot be null for this operation");
        }
        return animalRepository.findMammalById(String.valueOf(mammalId)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mammal with id: "+ mammalId + " not found"));
    }

    @Override
    public Mammal addMammal(Mammal mammal) {
        if (mammal == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mammal to add cannot be null");
        }
        else if (mammal.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mammal to add cannot contain an id");
        }
        else if (!zooRepository.existsById(mammal.getZoo().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add Mammal to a zoo that doesn't exist");
        }
        return animalRepository.save(mammal);
    }

    @Override
    public Mammal updateMammal(Mammal mammal) {
        if (mammal == null || mammal.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mammal to update must have an id");
        }
        else if (!animalRepository.existsById(mammal.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mammal to update cannot be found");
        }
        else if (!zooRepository.existsById(mammal.getZoo().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot update Mammal to a zoo that doesn't exist");
        }

        return animalRepository.save(mammal);
    }

    @Override
    public void deleteMammal(UUID mammalId) {
        if (mammalId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mammal id cannot be null for delete");
        }
        else if (!animalRepository.existsById(mammalId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Mammal to delete could not be found");
        }
        else {
            animalRepository.deleteById(mammalId);
        }
    }
}
