package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.entities.Mammal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMammalService {
    List<Mammal> findAllMammals();
}
