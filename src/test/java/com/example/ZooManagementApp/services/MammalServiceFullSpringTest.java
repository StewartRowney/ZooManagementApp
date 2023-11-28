package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import com.example.ZooManagementApp.entities.Mammal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MammalServiceFullSpringTest {

    @MockBean
    IAnimalRepository mockAnimalRepository;

    @Autowired
    IMammalService uut;

    @Test
    void test_FindAllMammals_ValidRequest() {
        uut.findAllMammals();
        verify(mockAnimalRepository, times(1)).findAllMammals();
    }

    @Test
    void test_FindMammalById_ValidRequest() {
        UUID mammalId = UUID.randomUUID();
        when(mockAnimalRepository.findMammalById(mammalId)).thenReturn(Optional.of(new Mammal()));
        uut.findMammalById(mammalId);
        verify(mockAnimalRepository, times(1)).findMammalById(mammalId);
    }

    @Test
    void test_FindMammalById_InvalidRequest_NotInDatabase() {
        UUID mammalId = UUID.randomUUID();
        assertThrows(ResponseStatusException.class, () -> uut.findMammalById(mammalId));
    }

    @Test
    void test_FindMammalById_InvalidRequest_NullID() {
        assertThrows(ResponseStatusException.class, () -> uut.findMammalById(null));
    }
}
