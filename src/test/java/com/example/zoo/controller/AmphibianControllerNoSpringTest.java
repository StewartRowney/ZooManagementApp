package com.example.zoo.controller;

import com.example.zoo.entities.Amphibian;
import com.example.zoo.services.IAmphibianService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

class AmphibianControllerNoSpringTest {

    private final IAmphibianService mockAmphibianService = mock(IAmphibianService.class);
    private final AmphibianController uut = new AmphibianController(mockAmphibianService);
    private final Amphibian amphibian = new Amphibian();
    private final UUID amphibianId = UUID.randomUUID();


    @Test
    void test_GetAllAmphibians() {
        uut.getAllAmphibians();
        verify(mockAmphibianService, times(1)).findAllAmphibians();
    }

    @Test
    void test_GetAmphibian() {
        uut.getAmphibian(amphibianId);
        verify(mockAmphibianService, times(1)).findAmphibianById(amphibianId);
    }

    @Test
    void test_AddAmphibian() {
        uut.addAmphibian(amphibian);
        verify(mockAmphibianService, times(1)).addAmphibian(amphibian);
    }

    @Test
    void test_UpdateAmphibian() {
        uut.updateAmphibian(amphibian);
        verify(mockAmphibianService, times(1)).updateAmphibian(amphibian);
    }

    @Test
    void test_DeleteAmphibian() {
        uut.deleteAmphibian(amphibianId);
        verify(mockAmphibianService, times(1)).deleteAmphibian(amphibianId);
    }
}
