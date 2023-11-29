package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.entities.Animal;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@SuppressWarnings({"unused"})
public class AnimalService implements IAnimalService{

    private final IAnimalRepository animalRepository;

    @Autowired
    public AnimalService(IAnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
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
}
