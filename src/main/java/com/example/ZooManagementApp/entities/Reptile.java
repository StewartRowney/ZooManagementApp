package com.example.ZooManagementApp.entities;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@SuppressWarnings({"unused"})
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
    public boolean isHasShell() {
        return hasShell;
    }

    public boolean isColdBlooded() {
        return isColdBlooded;
    }

    public boolean isHasLegs() {
        return hasLegs;
    }

    //SETTERS
    public void setHasShell(boolean hasShell) {
        this.hasShell = hasShell;
    }

    public void setColdBlooded(boolean coldBlooded) {
        isColdBlooded = coldBlooded;
    }

    public void setHasLegs(boolean hasLegs) {
        this.hasLegs = hasLegs;
    }
}
