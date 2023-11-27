package com.example.ZooManagementApp.data;

import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.entities.Mammal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IAnimalRepository extends ListCrudRepository<Animal, UUID> {

    @Query(value = "SELECT * FROM Animal WHERE DType = 'Mammal'", nativeQuery = true)
    List<Mammal> findAllMammals();
}
