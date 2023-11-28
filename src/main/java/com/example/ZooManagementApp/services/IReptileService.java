package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.entities.Reptile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IReptileService {
    List<Reptile> findAllReptiles();
}
