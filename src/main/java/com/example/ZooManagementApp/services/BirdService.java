package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.entities.Bird;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
}
