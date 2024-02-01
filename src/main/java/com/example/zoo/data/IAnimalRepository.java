package com.example.zoo.data;

import com.example.zoo.entities.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IAnimalRepository extends ListCrudRepository<Animal, UUID> {

    //FIND-ALL
    @Query(value = "SELECT * FROM ANIMAL WHERE ZOO_ID = :#{#zooId}", nativeQuery = true)
    List<Animal> findAllAnimalsInAZoo(@Param("zooId") UUID zooId);

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

    @Query(value = "SELECT * FROM Animal WHERE DType = 'Amphibian'", nativeQuery = true)
    List<Amphibian> findAllAmphibians();



    //FIND-BY-ID
    @Query(value = "SELECT * FROM Animal WHERE Id = :#{#id} AND DType = 'Mammal'", nativeQuery = true)
    Optional<Mammal> findMammalById(@Param("id") String id);

    @Query(value = "SELECT * FROM Animal WHERE Id = :#{#id} AND DType = 'Insect'", nativeQuery = true)
    Optional<Insect> findInsectById(@Param("id") String id);

    @Query(value = "SELECT * FROM Animal WHERE Id = :#{#id} AND DType = 'Fish'", nativeQuery = true)
    Optional<Fish> findFishById(@Param("id") String id);

    @Query(value = "SELECT * FROM Animal WHERE Id = :#{#id} AND DType = 'Reptile'", nativeQuery = true)
    Optional<Reptile> findReptileById(@Param("id") String id);

    @Query(value = "SELECT * FROM Animal WHERE Id = :#{#id} AND DType = 'Bird'", nativeQuery = true)
    Optional<Bird> findBirdById(@Param("id") String id);

    @Query(value = "SELECT * FROM Animal WHERE Id = :#{#id} AND DType = 'Amphibian'", nativeQuery = true)
    Optional<Amphibian> findAmphibianById(@Param("id") String id);

}
