package com.example.zoo.services;

import com.example.zoo.data.IAnimalRepository;
import com.example.zoo.data.ZooRepository;
import com.example.zoo.entities.Reptile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@SuppressWarnings({"unused"})
public class ReptileService implements IReptileService{

    private final IAnimalRepository animalRepository;
    private final ZooRepository zooRepository;

    @Autowired
    public ReptileService(IAnimalRepository animalRepository, ZooRepository zooRepository) {
        this.animalRepository = animalRepository;
        this.zooRepository = zooRepository;
    }

    public List<Reptile> findAllReptiles() {
        return animalRepository.findAllReptiles();
    }

    @Override
    public Reptile findReptileById(UUID reptileId) {
        if (reptileId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reptile id cannot be null for this operation");
        }
        return animalRepository.findReptileById(reptileId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reptile with id: "+ reptileId + " not found"));
    }

    @Override
    public Reptile addReptile(Reptile reptile) {
        if (reptile == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reptile to add cannot be null");
        }
        else if (reptile.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reptile to add cannot contain an id");
        }
        else if (!zooRepository.existsById(reptile.getZoo().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add Reptile to a zoo that doesn't exist");
        }
        return animalRepository.save(reptile);
    }

    @Override
    public Reptile updateReptile(Reptile reptile) {
        if (reptile == null || reptile.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reptile to update must have an id");
        }
        else if (!animalRepository.existsById(reptile.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reptile to update cannot be found");
        }
        else if (!zooRepository.existsById(reptile.getZoo().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot update Reptile to a zoo that doesn't exist");
        }

        return animalRepository.save(reptile);
    }

    @Override
    public void deleteReptile(UUID reptileId) {
        if (reptileId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Reptile id cannot be null for delete");
        }
        else if (!animalRepository.existsById(reptileId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reptile to delete could not be found");
        }
        else {
            animalRepository.deleteById(reptileId);
        }
    }
}
