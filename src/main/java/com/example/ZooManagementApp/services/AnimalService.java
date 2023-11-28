package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.data.ZooRepository;
import com.example.ZooManagementApp.entities.Amphibian;
import com.example.ZooManagementApp.entities.Animal;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AnimalService implements IAnimalService{

    private final IAnimalRepository animalRepository;
    private final ZooRepository zooRepository;

    @Autowired
    public AnimalService(IAnimalRepository animalRepository, ZooRepository zooRepository) {
        this.animalRepository = animalRepository;
        this.zooRepository = zooRepository;
    }

    @Override
    public List<Animal> findAllAnimals() {
        return animalRepository.findAll();
    }

    @Override
    public Animal findAnimalById(UUID id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Animal with id: "+ id+ " not found"));
    }

    @Override
    public List<Animal> addListOfAnimals(List<Animal> animals) {

        List<Animal> savedAnimals = null;

        for (Animal animal : animals) {

            savedAnimals.add(addListOfAnimals())
        }
    }

}
