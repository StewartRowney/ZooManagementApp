package com.example.zoo.entities;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@SuppressWarnings({"unused", "java:S107"})
public class Reptile extends Animal{

    //VARIABLES
    private boolean hasShell;
    private boolean isColdBlooded;
    private boolean hasLegs;

    //CONSTRUCTORS
    public Reptile() {
    }

    public Reptile(Zoo zoo, String name, String speciesName, LocalDate birthDate, String habitat, String behaviour, String foodType, String extraInformation, boolean hasShell, boolean isColdBlooded, boolean hasLegs) {
        super(zoo, name, speciesName, birthDate, habitat, behaviour, foodType, extraInformation);
        this.hasShell = hasShell;
        this.isColdBlooded = isColdBlooded;
        this.hasLegs = hasLegs;
    }

    //GETTERS
    public boolean getHasShell() {
        return hasShell;
    }

    public boolean getIsColdBlooded() {
        return isColdBlooded;
    }

    public boolean getHasLegs() {
        return hasLegs;
    }

    //SETTERS
    public void setHasShell(boolean hasShell) {
        this.hasShell = hasShell;
    }

    public void setIsColdBlooded(boolean coldBlooded) {
        isColdBlooded = coldBlooded;
    }

    public void setHasLegs(boolean hasLegs) {
        this.hasLegs = hasLegs;
    }
}
