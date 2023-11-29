package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.entities.Reptile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface IReptileService {

    List<Reptile> findAllReptiles();
    Reptile findReptileById(UUID reptileId);
    Reptile addReptile(Reptile reptile);
    Reptile updateReptile(Reptile reptile);
    void deleteReptile(UUID reptileId);

}
