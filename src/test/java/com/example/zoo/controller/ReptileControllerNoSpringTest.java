package com.example.zoo.controller;

import com.example.zoo.entities.Reptile;
import com.example.zoo.services.IReptileService;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

class ReptileControllerNoSpringTest {

    private final IReptileService mockReptileService = mock(IReptileService.class);
    private final ReptileController uut = new ReptileController(mockReptileService);
    private final Reptile reptile = new Reptile();
    private final UUID reptileId = UUID.randomUUID();


    @Test
    void test_GetAllReptiles() {
        uut.getAllReptiles();
        verify(mockReptileService, times(1)).findAllReptiles();
    }

    @Test
    void test_GetReptile() {
        uut.getReptile(reptileId);
        verify(mockReptileService, times(1)).findReptileById(reptileId);
    }

    @Test
    void test_AddReptile() {
        uut.addReptile(reptile);
        verify(mockReptileService, times(1)).addReptile(reptile);
    }

    @Test
    void test_UpdateReptile() {
        uut.updateReptile(reptile);
        verify(mockReptileService, times(1)).updateReptile(reptile);
    }

    @Test
    void test_DeleteReptile() {
        uut.deleteReptile(reptileId);
        verify(mockReptileService, times(1)).deleteReptile(reptileId);
    }
}
