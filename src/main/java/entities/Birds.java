package entities;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Birds extends Animal {

    //Variables
    private boolean canFly;
    private boolean isNocturnal;
    private boolean canMimicSound;

    //Constructors

    public Birds(Zoo zoo, String name, String speciesName, LocalDate birthDate, String habitat, String behaviour, String foodType, String extraInformation, boolean canFly, boolean isNocturnal, boolean canMimicSound) {
        super(zoo, name, speciesName, birthDate, habitat, behaviour, foodType, extraInformation);
        this.canFly = canFly;
        this.isNocturnal = isNocturnal;
        this.canMimicSound = canMimicSound;
    }

    public Birds() {
    }

    //Getters

    public boolean isCanFly() {
        return canFly;
    }

    public boolean isNocturnal() {
        return isNocturnal;
    }

    public boolean isCanMimicSound() {
        return canMimicSound;
    }

    //Setters

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    public void setNocturnal(boolean nocturnal) {
        isNocturnal = nocturnal;
    }

    public void setCanMimicSound(boolean canMimicSound) {
        this.canMimicSound = canMimicSound;
    }
}
