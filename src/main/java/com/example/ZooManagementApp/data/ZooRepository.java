package com.example.ZooManagementApp.data;

import entities.Zoo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ZooRepository extends ListCrudRepository<Zoo, UUID> {
}
