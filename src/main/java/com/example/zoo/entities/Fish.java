package com.example.zoo.entities;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@SuppressWarnings({"unused", "java:S107"})
public class Fish extends Animal{
    //Variables
    private boolean isBioluminiscent;
    private boolean canDischargeElectricity;

    //Constructors
    public Fish() {
    }

    public Fish(Zoo zoo, String name, String speciesName, LocalDate birthDate, String habitat, String behaviour, String foodType, String extraInformation, boolean isBioluminiscent, boolean canDischargeElectricity) {
        super(zoo, name, speciesName, birthDate, habitat, behaviour, foodType, extraInformation);
        this.isBioluminiscent = isBioluminiscent;
        this.canDischargeElectricity = canDischargeElectricity;
    }


    //Getters and Setters

    public boolean getIsBioluminiscent() {
        return isBioluminiscent;
    }

    public void setIsBioluminiscent(boolean bioluminiscent) {
        isBioluminiscent = bioluminiscent;
    }

    public boolean isCanDischargeElectricity() {
        return canDischargeElectricity;
    }

    public void setCanDischargeElectricity(boolean canDischargeElectricity) {
        this.canDischargeElectricity = canDischargeElectricity;
    }
}
