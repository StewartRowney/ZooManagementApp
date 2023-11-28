package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.entities.Bird;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public interface IBirdService {
    List<Bird> findAllBirds();

    Bird findBirdById(UUID id);


    Bird addNewBird(Bird bird);

    Bird updateBirdWithPut(Bird bird);

    void removeBirdById(UUID id);
}
