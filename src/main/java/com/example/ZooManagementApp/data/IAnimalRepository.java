package com.example.ZooManagementApp.data;

import com.example.ZooManagementApp.entities.Animal;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAnimalRepository extends ListCrudRepository<Animal, UUID> {
}
