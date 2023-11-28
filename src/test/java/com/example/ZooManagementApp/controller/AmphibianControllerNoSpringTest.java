package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Amphibian;
import com.example.ZooManagementApp.services.IAmphibianService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AmphibianControllerNoSpringTest {

    private IAmphibianService mockAmphibianService;
    private AmphibianController uut;
    private Amphibian amphibian;
    private UUID amphibianId;

    @BeforeEach
    void beforeEach(){
        this.mockAmphibianService = Mockito.mock(IAmphibianService.class);
        this.uut = new AmphibianController(mockAmphibianService);
        this.amphibian = new Amphibian();
        this.amphibianId = UUID.randomUUID();
    }

    @Test
    void testAmphibianServiceCalledForGetAllAmphibians() {
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
