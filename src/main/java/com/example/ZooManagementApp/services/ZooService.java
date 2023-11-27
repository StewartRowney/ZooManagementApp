package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.ZooRepository;
import com.example.ZooManagementApp.entities.Zoo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZooService implements IZooService{
    @Autowired
    ZooRepository repository;
    public List<Zoo> findAllZoos() {
        return repository.findAll();
    }
}
