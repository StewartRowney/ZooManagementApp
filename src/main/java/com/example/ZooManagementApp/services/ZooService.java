package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.data.ZooRepository;
import com.example.ZooManagementApp.entities.Zoo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ZooService implements IZooService{
    @Autowired
    ZooRepository repository;

    @Autowired
    IAnimalRepository animalRepository;
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
        if (zoo == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zoo to add cannot be null");
        }
        else if (zoo.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zoo to add cannot contain an id");
        }
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zoo to delete must have an Id");}
        if(!animalRepository.findAllAnimalsInAZoo(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zoo to delete must not have any animals");
        }
        if (!repository.existsById(id)) { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Zoo to update does not exist");}
        repository.deleteById(id);
    }

    @Override
    public List<Zoo> addListOfZoos(List<Zoo> zoos) throws JsonProcessingException {
        List<Zoo> zoosThatCannotBeAdded = new ArrayList<>();
        for (Zoo zoo : zoos) {
            try {
                addNewZoo(zoo);
            } catch (Exception e) {
                zoosThatCannotBeAdded.add(zoo);
            }
        }
        if(zoosThatCannotBeAdded.size()!=0){
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            String zoosJson = mapper.writeValueAsString(zoosThatCannotBeAdded);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some zoos were not added:\n " + zoosJson);
        }
        return zoos;
    }
}

//Todo: Don't delete Zoo if there's still animals in it.