package com.example.ZooManagementApp.data;

import com.example.ZooManagementApp.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@Profile("!test")
public class Populator {

    private final ZooRepository zooRepository;
    private final IAnimalRepository animalRepository;

    @Autowired
    public Populator(ZooRepository zooRepository, IAnimalRepository animalRepository) {
        this.zooRepository = zooRepository;
        this.animalRepository = animalRepository;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void populate(){
        Zoo chesterZoo = new Zoo("Chester Zoo", "Upton-by-Chester, Cheshire, England", 27000, BigDecimal.valueOf(19.00), LocalDate.of( 1931,  6, 10));
        zooRepository.save(chesterZoo);

        Zoo edinburghZoo = new Zoo("Edinburgh Zoo", "Corstorphine,  Edinburgh, Scotland", 2500, BigDecimal.valueOf(22.50), LocalDate.of( 1913,  7, 22));
        zooRepository.save(edinburghZoo);

        Zoo londonZoo = new Zoo("London Zoo", "Regent's Park, London, England", 20166, BigDecimal.valueOf(27.00), LocalDate.of( 1828,  4, 27));
        zooRepository.save(londonZoo);


        Animal tiger = new Mammal(chesterZoo, "Barry", "Tiger", LocalDate.of(2013, 3, 11), "Jungle", "angry and ferocious", "meat", "He is playful when calm but extremely aggressive when provoked", true, false, false);
        animalRepository.save(tiger);

        Animal pigeon = new Birds(chesterZoo, "Henry", "Pigeon", LocalDate.of(2022, 1, 18), "Woods", "calm and chirpy", "seeds", "Annoying and noisy!", true, false, false);
        animalRepository.save(pigeon);

        Animal snake = new Reptile(chesterZoo, "Slimy", "Snake", LocalDate.of(2000, 11, 22), "Rainforest", "Hungry and territorial", "rodents", "Chills most of the time, sleeps and stares alot", false, true, false);
        animalRepository.save(snake);

        Animal badger = new Mammal(edinburghZoo, "Bill", "Badger", LocalDate.of(1996, 3, 9), "Woods", "Likes digging", "rodents, vermin, insects", "Eats whatever it can get its paws on", true, false, false);
        animalRepository.save(badger);

        Animal goldfish = new Fish(edinburghZoo, "Goldie", "Goldfish", LocalDate.of(2023, 07, 29), "Ocean", "Literally unknown, does nothing, exists", "flakes", "A very boring/uncool animal", false, false);
        animalRepository.save(goldfish);
    }

}
