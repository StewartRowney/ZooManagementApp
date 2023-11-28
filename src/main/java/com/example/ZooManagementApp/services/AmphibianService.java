package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.entities.Amphibian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AmphibianService implements IAmphibianService{

    private final IAnimalRepository animalRepository;

    @Autowired
    public AmphibianService(IAnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public List<Amphibian> findAllAmphibians() {
        return animalRepository.findAllAmphibians();
    }
}
