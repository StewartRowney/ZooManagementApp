package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.entities.Fish;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface IFishService {
    List<Fish> findAllFish();
    Fish findFishById(UUID id);
    Fish addFish(Fish fish);
    Fish updateFish(Fish fish);
    void deleteFish(UUID id);
}
