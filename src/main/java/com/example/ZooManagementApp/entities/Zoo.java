package com.example.ZooManagementApp.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Zoo {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String location;
    private int capacity;
    @OneToMany
    private final List<Animal> animals = new ArrayList<>();
    private BigDecimal price;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateOpened;


    //Getters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getCapacity() {
        return capacity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDate getDateOpened() {
        return dateOpened;
    }


    //Setter
    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setDateOpened(LocalDate dateOpened) {
        this.dateOpened = dateOpened;
    }


    //Constructors
    public Zoo(String name, String location, int capacity, BigDecimal price, LocalDate dateOpened) {
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.price = price;
        this.dateOpened = dateOpened;
    }

    public Zoo(){

    }


}
