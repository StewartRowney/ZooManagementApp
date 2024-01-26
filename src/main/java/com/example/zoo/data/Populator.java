package com.example.zoo.data;

import com.example.zoo.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Component
@SuppressWarnings({"unused", "java:S2677"})
@Profile("!test")
public class Populator {

    private final ZooRepository zooRepository;
    private final IAnimalRepository animalRepository;
    private final List<Zoo> zoos = new ArrayList<>();
    private final Random random = new Random();
    private final Logger logger = Logger.getLogger(getClass().getName());

    @Autowired
    public Populator(ZooRepository zooRepository, IAnimalRepository animalRepository) {
        this.zooRepository = zooRepository;
        this.animalRepository = animalRepository;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void populate(){
        populateZoos();
        populateAmphibians();
        populateBirds();
        populateFish();
        populateInsect();
        populateMammals();
        populateReptiles();
    }

    private void populateZoos() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/zoo-data.csv"))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                Zoo zoo = new Zoo(values[0], values[1], Integer.parseInt(values[2]), BigDecimal.valueOf(Double.parseDouble(values[3])), LocalDate.parse(values[4]));
                zooRepository.save(zoo);
                zoos.add(zoo);
            }
        } catch (IOException e) {
            logger.info("Error populating Zoos with csv file");
        }
    }

    private void populateAmphibians() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/amphibian-data.csv"))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Zoo zoo = zoos.get(random.nextInt(zoos.size()));

                Amphibian amphibian = new Amphibian(zoo, values[0], values[1], LocalDate.parse(values[2]), values[3], values[4], values[5], values[6],
                        Boolean.parseBoolean(values[7]), Boolean.parseBoolean(values[8]));
                animalRepository.save(amphibian);
            }
        } catch (IOException e) {
            logger.info("Error populating Amphibians with csv file");
        }
    }

    private void populateBirds() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/bird-data.csv"))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Zoo zoo = zoos.get(random.nextInt(zoos.size()));

                Bird bird = new Bird(zoo, values[0], values[1], LocalDate.parse(values[2]), values[3], values[4], values[5], values[6],
                        Boolean.parseBoolean(values[7]), Boolean.parseBoolean(values[8]), Boolean.parseBoolean(values[9]));
                animalRepository.save(bird);
            }
        } catch (IOException e) {
            logger.info("Error populating Birds with csv file");
        }
    }

    private void populateFish() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/fish-data.csv"))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Zoo zoo = zoos.get(random.nextInt(zoos.size()));

                Fish fish = new Fish(zoo, values[0], values[1], LocalDate.parse(values[2]), values[3], values[4], values[5], values[6],
                        Boolean.parseBoolean(values[7]), Boolean.parseBoolean(values[8]));
                animalRepository.save(fish);
            }
        } catch (IOException e) {
            logger.info("Error populating Fish with csv file");
        }
    }

    private void populateInsect() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/insect-data.csv"))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Zoo zoo = zoos.get(random.nextInt(zoos.size()));

                Insect insect = new Insect(zoo, values[0], values[1], LocalDate.parse(values[2]), values[3], values[4], values[5], values[6],
                        Boolean.parseBoolean(values[7]), Integer.parseInt(values[8]));
                animalRepository.save(insect);
            }
        } catch (IOException e) {
            logger.info("Error populating Insects with csv file");
        }
    }

    private void populateMammals() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/mammal-data.csv"))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Zoo zoo = zoos.get(random.nextInt(zoos.size()));

                Mammal mammal = new Mammal(zoo, values[0], values[1], LocalDate.parse(values[2]), values[3], values[4], values[5], values[6],
                        Boolean.parseBoolean(values[7]), Boolean.parseBoolean(values[8]), Boolean.parseBoolean(values[9]));
                animalRepository.save(mammal);
            }
        } catch (IOException e) {
            logger.info("Error populating Mammals with csv file");
        }
    }

    private void populateReptiles() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/reptile-data.csv"))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Zoo zoo = zoos.get(random.nextInt(zoos.size()));

                Reptile reptile = new Reptile(zoo, values[0], values[1], LocalDate.parse(values[2]), values[3], values[4], values[5], values[6],
                        Boolean.parseBoolean(values[7]), Boolean.parseBoolean(values[8]), Boolean.parseBoolean(values[9]));
                animalRepository.save(reptile);
            }
        } catch (IOException e) {
            logger.info("Error populating Reptiles with csv file");
        }
    }
}
