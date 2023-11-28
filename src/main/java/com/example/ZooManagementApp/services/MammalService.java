package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.entities.Mammal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MammalService implements IMammalService{

    private final IAnimalRepository animalRepository;

    @Autowired
    public MammalService(IAnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public List<Mammal> findAllMammals() {
        return animalRepository.findAllMammals();
    }

    @Override
    public Mammal findMammalById(UUID mammalId) {
        return null;
    }
}
