package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.entities.Reptile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReptileService implements IReptileService{

    private final IAnimalRepository animalRepository;

    @Autowired
    public ReptileService(IAnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public List<Reptile> findAllReptiles() {
        return animalRepository.findAllReptiles();
    }
}
