package com.example.zoo.services;

import com.example.zoo.data.IAnimalRepository;
import com.example.zoo.data.ZooRepository;
import com.example.zoo.entities.Zoo;
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

    private final ZooRepository repository;
    private final IAnimalRepository animalRepository;

    @Autowired
    public ZooService(ZooRepository repository, IAnimalRepository animalRepository) {
        this.repository = repository;
        this.animalRepository = animalRepository;
    }

    public List<Zoo> findAllZoos() {
        return repository.findAll();
    }

    @Override
    public Zoo findZooById(UUID id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id cannot be null");
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
    public Zoo updateZoo(Zoo zoo) {
        if (zoo == null || zoo.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zoo to update must have an Id");
        }

        if (!repository.existsById(zoo.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Zoo to update does not exist");}
        return repository.save(zoo);
    }
    @Override
    public Zoo updateZooName(String name, UUID id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zoo to update must have an Id");}
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Zoo to update name does not exist");}
        Zoo zooToUpdate = findZooById(id);
        zooToUpdate.setName(name);
        return repository.save(zooToUpdate);}
    @Override
    public void removeZooById(UUID id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zoo to delete must have an Id");}
        if(!animalRepository.findAllAnimalsInAZoo(String.valueOf(id)).isEmpty()){
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
        if(!zoosThatCannotBeAdded.isEmpty()){
            ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
            String zoosJson = mapper.writeValueAsString(zoosThatCannotBeAdded);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Some zoos were not added:\n " + zoosJson);
        }
        return zoos;
    }
}
