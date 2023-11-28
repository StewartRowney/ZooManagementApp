package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.entities.Amphibian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class AmphibianService implements IAmphibianService{

    private final IAnimalRepository animalRepository;

    @Autowired
    public AmphibianService(IAnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public List<Amphibian> findAllAmphibians() {
        return animalRepository.findAllAmphibians();
    }

    @Override
    public Amphibian findAmphibianById(UUID amphibianId) {
        if (amphibianId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amphibian id cannot be null for this operation");
        }
        return animalRepository.findAmphibianById(amphibianId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Amphibian with id: "+ amphibianId + " not found"));
    }

    @Override
    public Amphibian addAmphibian(Amphibian amphibian) {
        if (amphibian.getId() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot set Amphibian id, set value to null");
        }
        return animalRepository.save(amphibian);
    }

    @Override
    public Amphibian updateAmphibian(Amphibian amphibian) {
        if (amphibian == null || amphibian.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amphibian to update must have an Id");
        }

        if (!animalRepository.existsById(amphibian.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Amphibian to update does not exist");
        }
        return animalRepository.save(amphibian);
    }

    @Override
    public void deleteAmphibian(UUID amphibianId) {
        if (amphibianId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Amphibian id cannot be null");
        }

        if (!animalRepository.existsById(amphibianId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Amphibian to delete does not exist");
        }
        animalRepository.deleteById(amphibianId);
    }
}
