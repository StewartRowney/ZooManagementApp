package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.entities.Insect;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IInsectService {
    List<Insect> findAllInsects();
}
