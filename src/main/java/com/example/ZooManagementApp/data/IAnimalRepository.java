package com.example.ZooManagementApp.data;

import com.example.ZooManagementApp.entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IAnimalRepository extends ListCrudRepository<Animal, UUID> {

    @Query(value = "SELECT * FROM Animal WHERE DType = 'Mammal'", nativeQuery = true)
    List<Mammal> findAllMammals();

    @Query(value = "SELECT * FROM Animal WHERE DType = 'Insect'", nativeQuery = true)
    List<Insect> findAllInsects();

    @Query(value = "SELECT * FROM Animal WHERE DType = 'Fish'", nativeQuery = true)
    List<Fish> findAllFish();

    @Query(value = "SELECT * FROM Animal WHERE DType = 'Reptile'", nativeQuery = true)
    List<Reptile> findAllReptiles();

    @Query(value = "SELECT * FROM Animal WHERE DType = 'Bird'", nativeQuery = true)
    List<Bird> findAllBirds();


    //AMPHIBIANS
    @Query(value = "SELECT * FROM Animal WHERE DType = 'Amphibian'", nativeQuery = true)
    List<Amphibian> findAllAmphibians();

    @Query(value = "SELECT * FROM Animal WHERE Id = :#{#id}", nativeQuery = true)
    Optional<Bird> findBirdById(@Param("id") UUID id);

    @Query(value = "SELECT * FROM Animal WHERE Id = :#{#id}", nativeQuery = true)
    Optional<Amphibian> findAmphibianById(@Param("id") UUID id);


    @Query(value = "SELECT * FROM Animal WHERE Id = :#{#id}", nativeQuery = true)
    Optional<Mammal> findMammalById(@Param("id") UUID id);
}
