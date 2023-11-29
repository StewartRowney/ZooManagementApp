package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.entities.Amphibian;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface IAmphibianService {
    List<Amphibian> findAllAmphibians();

    Amphibian findAmphibianById(UUID amphibianId);

    Amphibian addAmphibian(Amphibian amphibian);

    Amphibian updateAmphibian(Amphibian amphibian);

    void deleteAmphibian(UUID amphibianId);
}
