package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.data.ZooRepository;
import com.example.ZooManagementApp.entities.Amphibian;
import com.example.ZooManagementApp.entities.Mammal;
import com.example.ZooManagementApp.entities.Zoo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AmphibianService implements IAmphibianService{

    private final IAnimalRepository animalRepository;
    private final ZooRepository zooRepository;

    @Autowired
    public AmphibianService(IAnimalRepository animalRepository, ZooRepository zooRepository) {
        this.animalRepository = animalRepository;
        this.zooRepository = zooRepository;
    }

    @Override
    public List<Amphibian> findAllAmphibians() {
        return animalRepository.findAllAmphibians();
    }

    @Override
    public Amphibian findAmphibianById(UUID amphibianId) {
        if (amphibianId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amphibian id cannot be null for this operation");
        }
        return animalRepository.findAmphibianById(amphibianId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Amphibian with id: "+ amphibianId + " not found"));
    }

    @Override
    public Amphibian addAmphibian(Amphibian amphibian) {
        if (amphibian == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amphibian to add cannot be null");
        }
        else if (amphibian.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amphibian to add cannot contain an id");
        }
        else if (!zooRepository.existsById(amphibian.getZoo().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add Amphibian to a zoo that doesn't exist");
        }
        return animalRepository.save(amphibian);
    }

    @Override
    public Amphibian updateAmphibian(Amphibian amphibian) {
        if (amphibian == null || amphibian.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amphibian to update must have an id");
        }
        else if (!animalRepository.existsById(amphibian.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Amphibian to update cannot be found");
        }
        else if (!zooRepository.existsById(amphibian.getZoo().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot update Amphibian to a zoo that doesn't exist");
        }

        return animalRepository.save(amphibian);
    }

    @Override
    public void deleteAmphibian(UUID amphibianId) {
        if (amphibianId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amphibian id cannot be null for delete");
        }
        else if (!animalRepository.existsById(amphibianId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Amphibian to delete could not be found");
        }
        else {
            animalRepository.deleteById(amphibianId);
        }
    }
}
