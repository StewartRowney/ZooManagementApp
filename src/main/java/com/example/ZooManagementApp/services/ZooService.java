package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.ZooRepository;
import com.example.ZooManagementApp.entities.Zoo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ZooService implements IZooService{
    @Autowired
    ZooRepository repository;
    public List<Zoo> findAllZoos() {
        return repository.findAll();
    }

    @Override
    public Zoo findZooById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Zoo with id: "+ id+ " not found"));
    }

    @Override
    public Zoo findZooByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Zoo with name: "+ name+ " not found"));
    }
}
