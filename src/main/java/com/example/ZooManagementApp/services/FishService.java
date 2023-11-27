package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.entities.Fish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FishService implements IFishService {

    private final IAnimalRepository animalRepository;

    @Autowired
    public FishService(IAnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public List<Fish> getAllFish() {
        return animalRepository.findAllFish();
    }
}
