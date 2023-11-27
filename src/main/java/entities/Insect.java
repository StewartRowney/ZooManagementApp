package entities;

import java.time.LocalDate;

public class Insect extends Animal{

    //VARIABLES
    private boolean hasWings;
    private int numberOfLegs;


    //CONSTRUCTORS
    public Insect() {
    }
    public Insect(Zoo zoo, String name, String speciesName, LocalDate birthDate, String habitat, String behaviour, String foodType, String extraInformation, boolean hasWings, int numberOfLegs) {
        super(zoo, name, speciesName, birthDate, habitat, behaviour, foodType, extraInformation);
        this.hasWings = hasWings;
        this.numberOfLegs = numberOfLegs;
    }

    //GETTERS
    public boolean isHasWings() {
        return hasWings;
    }
    public int getNumberOfLegs() {
        return numberOfLegs;
    }


    //SETTERS
    public void setHasWings(boolean hasWings) {
        this.hasWings = hasWings;
    }
    public void setNumberOfLegs(int numberOfLegs) {
        this.numberOfLegs = numberOfLegs;
    }
}
