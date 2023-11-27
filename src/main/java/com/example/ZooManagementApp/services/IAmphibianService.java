package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.entities.Amphibian;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface IAmphibianService {
    List<Amphibian> getAllAmphibians();
}
