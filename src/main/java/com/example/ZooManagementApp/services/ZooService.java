package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.ZooRepository;
import com.example.ZooManagementApp.entities.Zoo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zoo to update must have an Id");
        }
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Zoo with id: "+ id+ " not found"));
    }

    @Override
    public Zoo findZooByName(String name) {

        return repository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Zoo with name: "+ name+ " not found"));
    }

    @Override
    public Zoo addNewZoo(Zoo zoo) {
        return repository.save(zoo);
    }

    @Override
    public Zoo updateZooWithPut(Zoo zoo) {
        if (zoo == null || zoo.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zoo to update must have an Id");
        }

        if (!repository.existsById(zoo.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Zoo to update does not exist");}
        return repository.save(zoo);
    }
    @Override
    public Zoo updateZooByName(String name, UUID id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zoo to update must have an Id");}
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Zoo to update does not exist");}
        Zoo zooToUpdate = findZooById(id);
        zooToUpdate.setName(name);
        return repository.save(zooToUpdate);}
    @Override
    public void removeZooById(UUID id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zoo to delete must have an Id");
        }

        if (!repository.existsById(id)) { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Zoo to update does not exist");}
        repository.deleteById(id);
    }

    @Override
    public List<Zoo> addListOfZoos(List<Zoo> zoos) {
        for (Zoo zoo : zoos) {
                addNewZoo(zoo);
        }
        return zoos;
    }
}

//Todo: Don't delete Zoo if there's still animals in it.