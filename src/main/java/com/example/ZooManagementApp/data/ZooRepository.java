package com.example.ZooManagementApp.data;

import com.example.ZooManagementApp.entities.Zoo;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ZooRepository extends ListCrudRepository<Zoo, UUID> {
    Optional<Zoo> findByName(String name);
}
