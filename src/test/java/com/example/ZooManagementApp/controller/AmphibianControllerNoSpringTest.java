package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.services.IAmphibianService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class AmphibianControllerNoSpringTest {

    IAmphibianService mockAmphibianService;
    AmphibianController uut;

    @BeforeEach
    void beforeEach(){
        mockAmphibianService = Mockito.mock(IAmphibianService.class);
        uut = new AmphibianController(mockAmphibianService);
    }

    @Test
    void testAmphibianServiceCalledForGetAllAmphibians() {
        uut.getAllAmphibians();
        verify(mockAmphibianService, times(1)).findAllAmphibians();
    }
}
