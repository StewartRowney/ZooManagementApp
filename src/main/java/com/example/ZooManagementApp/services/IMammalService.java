package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.entities.Mammal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface IMammalService {
    List<Mammal> findAllMammals();
    Mammal findMammalById(UUID mammalId);
    Mammal addMammal(Mammal mammal);
    Mammal updateMammal(Mammal mammal);
}
