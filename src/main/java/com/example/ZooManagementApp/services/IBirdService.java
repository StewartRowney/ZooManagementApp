package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.entities.Birds;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface IBirdService {
    List<Birds> findAllBirds();
}
