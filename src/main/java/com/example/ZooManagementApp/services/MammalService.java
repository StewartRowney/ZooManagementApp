package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.entities.Mammal;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MammalService implements IMammalService{

    private final IAnimalRepository animalRepository;

    @Autowired
    public MammalService(IAnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public List<Mammal> getAllMammals() {
        return animalRepository.findAllMammals();
    }
}
