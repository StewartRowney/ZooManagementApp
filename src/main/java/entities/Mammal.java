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
    private boolean hasHooves;


    //CONSTRUCTORS
    public Mammal() {}

    public Mammal(Zoo zoo, String name, String speciesName, LocalDate birthDate, String habitat, String behaviour, String foodType, String extraInformation, boolean hasFur, boolean hasFins, boolean hasTrunk, boolean hasTusks, boolean hasHooves, boolean isCarnivorous) {
        super(zoo, name, speciesName, birthDate, habitat, behaviour, foodType, extraInformation);
        this.hasFur = hasFur;
        this.hasFins = hasFins;
        this.hasHooves = hasHooves;
    }

    //GETTERS
    public boolean isHasFur() {
        return hasFur;
    }

    public boolean isHasFins() {
        return hasFins;
    }

    public boolean isHasHooves() {
        return hasHooves;
    }

    //SETTERS
    public void setHasFur(boolean hasFur) {
        this.hasFur = hasFur;
    }

    public void setHasFins(boolean hasFins) {
        this.hasFins = hasFins;
    }

    public void setHasHooves(boolean hasHooves) {
        this.hasHooves = hasHooves;
    }

}
