package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.entities.Insect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class InsectService implements IInsectService{

    private final IAnimalRepository animalRepository;

    @Autowired
    public InsectService(IAnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public List<Insect> findAllInsects() {
        return animalRepository.findAllInsects();
    }

    @Override
    public Insect findInsectById(UUID insectId) {
        return null;
    }
}
