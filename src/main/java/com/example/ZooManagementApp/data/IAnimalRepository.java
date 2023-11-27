package com.example.ZooManagementApp.data;

import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.entities.Fish;
import com.example.ZooManagementApp.entities.Mammal;
import org.springframework.data.jpa.repository.Query;
import com.example.ZooManagementApp.entities.Insect;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IAnimalRepository extends ListCrudRepository<Animal, UUID> {

    @Query(value = "SELECT * FROM Animal WHERE DType = 'Mammal'", nativeQuery = true)
    List<Mammal> findAllMammals();

    @Query(value = "SELECT * FROM Animal WHERE DType = 'Insect'", nativeQuery = true)
    List<Insect> findAllInsects();

    @Query(value = "SELECT * FROM Animal WHERE DType = 'Fish'", nativeQuery = true)
    List<Fish> findAllFish();
}
