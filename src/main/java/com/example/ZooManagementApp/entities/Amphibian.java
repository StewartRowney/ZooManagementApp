package com.example.ZooManagementApp.entities;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@SuppressWarnings({"unused"})
public class Amphibian extends Animal{

    //VARIABLES
    private boolean isPoisonous;
    private boolean makesNoise;

    //CONSTRUCTORS
    public Amphibian() {
    }
    public Amphibian(Zoo zoo, String name, String speciesName, LocalDate birthDate, String habitat, String behaviour, String foodType, String extraInformation, boolean isPoisonous, boolean makesNoise) {
        super(zoo, name, speciesName, birthDate, habitat, behaviour, foodType, extraInformation);
        this.isPoisonous = isPoisonous;
        this.makesNoise = makesNoise;
    }

    //GETTERS
    public boolean isPoisonous() {
        return isPoisonous;
    }
    public boolean isMakesNoise() {
        return makesNoise;
    }

    //SETTERS
    public void setPoisonous(boolean poisonous) {
        isPoisonous = poisonous;
    }
    public void setMakesNoise(boolean makesNoise) {
        this.makesNoise = makesNoise;
    }
}
