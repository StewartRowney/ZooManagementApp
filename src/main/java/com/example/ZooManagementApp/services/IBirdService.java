package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.entities.Bird;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface IBirdService {
    List<Bird> findAllBirds();
}
