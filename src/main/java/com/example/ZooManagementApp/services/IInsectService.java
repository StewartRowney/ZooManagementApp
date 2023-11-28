package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.entities.Insect;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface IInsectService {
    List<Insect> findAllInsects();
    Insect findInsectById(UUID insectId);
    Insect addInsect(Insect insect);
    Insect updateInsect(Insect insect);
    void deleteInsectById(UUID insectId);
}
