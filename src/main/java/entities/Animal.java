package entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Animal {


    //VARIABLES
    @Id
    @GeneratedValue
    private UUID id;
    @ManyToOne
    private Zoo zoo;
    private String name;
    private String speciesName;
    private LocalDate birthDate;
    private String habitat;
    private String behaviour;
    private String foodType;
    private String extraInformation;

    //CONSTRUCTORS
    public Animal() {
    }

    public Animal(Zoo zoo, String name, String speciesName, LocalDate birthDate, String habitat, String behaviour, String foodType, String extraInformation) {
        this.zoo = zoo;
        this.name = name;
        this.speciesName = speciesName;
        this.birthDate = birthDate;
        this.habitat = habitat;
        this.behaviour = behaviour;
        this.foodType = foodType;
        this.extraInformation = extraInformation;
    }

    //GETTERS
    public UUID getId() {
        return id;
    }
    public Zoo getZoo() {
        return zoo;
    }
    public String getName() {
        return name;
    }
    public String getSpeciesName() {
        return speciesName;
    }
    public LocalDate getBirthDate() {
        return birthDate;
    }
    public String getHabitat() {
        return habitat;
    }
    public String getBehaviour() {
        return behaviour;
    }
    public String getFoodType() {
        return foodType;
    }
    public String getExtraInformation() {
        return extraInformation;
    }

    //SETTERS
    public void setZoo(Zoo zoo) {
        this.zoo = zoo;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSpeciesName(String speciesName) {
        this.speciesName = speciesName;
    }
    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }
    public void setBehaviour(String behaviour) {
        this.behaviour = behaviour;
    }
    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }
    public void setExtraInformation(String extraInformation) {
        this.extraInformation = extraInformation;
    }
}
