package entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class Mammal extends Animal{

    //VARIABLES
    private boolean hasFur;
    private boolean hasFins;
    private boolean hasTrunk;
    private boolean hasTusks;
    private boolean hasHooves;
    private boolean isCarnivorous;

    //CONSTRUCTORS
    public Mammal() {}

    public Mammal(Zoo zoo, String name, String speciesName, LocalDate birthDate, String habitat, String behaviour, String foodType, String extraInformation, boolean hasFur, boolean hasFins, boolean hasTrunk, boolean hasTusks, boolean hasHooves, boolean isCarnivorous) {
        super(zoo, name, speciesName, birthDate, habitat, behaviour, foodType, extraInformation);
        this.hasFur = hasFur;
        this.hasFins = hasFins;
        this.hasTrunk = hasTrunk;
        this.hasTusks = hasTusks;
        this.hasHooves = hasHooves;
        this.isCarnivorous = isCarnivorous;
    }

    //GETTERS
    public boolean isHasFur() {
        return hasFur;
    }

    public boolean isHasFins() {
        return hasFins;
    }

    public boolean isHasTrunk() {
        return hasTrunk;
    }

    public boolean isHasTusks() {
        return hasTusks;
    }

    public boolean isHasHooves() {
        return hasHooves;
    }

    public boolean isCarnivorous() {
        return isCarnivorous;
    }

    //SETTERS
    public void setHasFur(boolean hasFur) {
        this.hasFur = hasFur;
    }

    public void setHasFins(boolean hasFins) {
        this.hasFins = hasFins;
    }

    public void setHasTrunk(boolean hasTrunk) {
        this.hasTrunk = hasTrunk;
    }

    public void setHasTusks(boolean hasTusks) {
        this.hasTusks = hasTusks;
    }

    public void setHasHooves(boolean hasHooves) {
        this.hasHooves = hasHooves;
    }

    public void setCarnivorous(boolean carnivorous) {
        isCarnivorous = carnivorous;
    }
}
