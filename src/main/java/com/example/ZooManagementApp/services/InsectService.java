package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.data.ZooRepository;
import com.example.ZooManagementApp.entities.Insect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class InsectService implements IInsectService{

    private final IAnimalRepository animalRepository;
    private final ZooRepository zooRepository;

    @Autowired
    public InsectService(IAnimalRepository animalRepository, ZooRepository zooRepository) {
        this.animalRepository = animalRepository;
        this.zooRepository = zooRepository;
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

    @Override
    public Insect addInsect(Insect insect) {
        if (insect == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insect to add cannot be null");
        }
        else if (insect.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insect to add cannot contain an id");
        }
        else if (!zooRepository.existsById(insect.getZoo().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot add Insect to a zoo that doesn't exist");
        }
        return animalRepository.save(insect);
    }

    @Override
    public Insect updateInsect(Insect insect) {
        return null;
    }

    @Override
    public void deleteInsectById(UUID insectId) {

    }
}
