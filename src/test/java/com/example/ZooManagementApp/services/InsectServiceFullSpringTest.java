package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.data.ZooRepository;
import com.example.ZooManagementApp.entities.Insect;
import com.example.ZooManagementApp.entities.Mammal;
import com.example.ZooManagementApp.entities.Zoo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class InsectServiceFullSpringTest {

    @MockBean
    private IAnimalRepository mockAnimalRepository;

    @MockBean
    ZooRepository mockZooRepository;

    @Autowired
    private IInsectService uut;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void test_GetAllInsects_ValidRequest() {
        uut.findAllInsects();
        verify(mockAnimalRepository,times(1)).findAllInsects();
    }

    @Test
    void test_GetInsectById_ValidRequest() {
        UUID insectId = UUID.randomUUID();
        when(mockAnimalRepository.findInsectById(insectId)).thenReturn(Optional.of(new Insect()));
        uut.findInsectById(insectId);
        verify(mockAnimalRepository,times(1)).findInsectById(insectId);
    }

    @Test
    void test_GetInsectByID_InvalidRequest_NotInDatabase() {
        UUID insectId = UUID.randomUUID();
        assertThrows(ResponseStatusException.class, () -> uut.findInsectById(insectId));
    }

    @Test
    void test_GetInsectByID_InvalidRequest_NullId() {
        assertThrows(ResponseStatusException.class, () -> uut.findInsectById(null));
    }

    @Test
    void test_AddInsect_ValidRequest() {
        Insect insect = new Insect();
        insect.setZoo(createZoo());
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(true);
        uut.addInsect(insect);
        verify(mockAnimalRepository, times(1)).save(insect);
    }

    @Test
    void test_AddInsect_InvalidRequest_ZooNotInDatabase() {
        Insect insect = new Insect();
        insect.setZoo(createZoo());
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.addInsect(insect));
    }

    @Test
    void test_AddInsect_InvalidRequest_HasId() {
        Insect insect = createInsect();
        assertThrows(ResponseStatusException.class, () -> uut.addInsect(insect));
    }

    @Test
    void test_AddInsect_InvalidRequest_NullInsect() {
        assertThrows(ResponseStatusException.class, () -> uut.addInsect(null));
    }

    @Test
    void test_UpdateInsect_ValidRequest() {
        Insect insect = createInsect();
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(true);
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(true);
        uut.updateInsect(insect);
        verify(mockAnimalRepository, times(1)).save(insect);
    }

    @Test
    void test_UpdateInsect_InvalidRequest_InsectNotInDatabase() {
        Insect insect = createInsect();
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.updateInsect(insect));
    }

    @Test
    void test_UpdateInsect_InvalidRequest_ZooNotInDatabase() {
        Insect insect = createInsect();
        when(mockAnimalRepository.existsById(any(UUID.class))).thenReturn(true);
        when(mockZooRepository.existsById(any(UUID.class))).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.updateInsect(insect));
    }

    @Test
    void test_UpdateInsect_InvalidRequest_HasNoId() {
        Insect insect = new Insect();
        assertThrows(ResponseStatusException.class, () -> uut.updateInsect(insect));
    }

    @Test
    void test_UpdateInsect_InvalidRequest_NullInsect() {
        assertThrows(ResponseStatusException.class, () -> uut.updateInsect(null));
    }

    @Test
    void test_DeleteInsectById_ValidRequest() {
        UUID insectId = UUID.randomUUID();
        when(mockAnimalRepository.existsById(insectId)).thenReturn(true);
        uut.deleteInsectById(insectId);
        verify(mockAnimalRepository, times(1)).deleteById(insectId);
    }

    @Test
    void test_DeleteById_InvalidRequest_NotInDatabase() {
        UUID insectId = UUID.randomUUID();
        when(mockAnimalRepository.existsById(insectId)).thenReturn(false);
        assertThrows(ResponseStatusException.class, () -> uut.deleteInsectById(insectId));
    }

    @Test
    void test_DeleteById_InvalidRequest_NullId() {
        assertThrows(ResponseStatusException.class, () -> uut.deleteInsectById(null));
    }

    private Insect createInsect() {
        String json = """
                {
                    "id": "481c8ba9-64cb-4c5d-84b1-1c22f04f86ea",
                    "name": "Sid",
                    "speciesName": "Spider",
                    "birthDate": "10-10-2020",
                    "habitat": "Trees",
                    "behaviour": "Crazy",
                    "foodType": "Flies",
                    "extraInformation": "is a great guy",
                    "hasWings": false,
                    "numberOfLegs": 8
                  }""";

        try {
            Insect insect = mapper.readValue(json, Insect.class);
            insect.setZoo(createZoo());
            return insect;
        } catch (JsonProcessingException e) {
            return new Insect();
        }
    }
    
    private Zoo createZoo() {
        String json = """
                 {
                    "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
                    "name": "string",
                    "location": "string",
                    "capacity": 0,
                    "price": 0,
                    "dateOpened": "12-05-1999"
                  }""";
        try {
            return mapper.readValue(json, Zoo.class);
        } catch (JsonProcessingException e) {
            return new Zoo();
        }
    }

}
