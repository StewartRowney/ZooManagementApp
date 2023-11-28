package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Reptile;
import com.example.ZooManagementApp.services.IReptileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.UUID;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ReptileControllerNoSpringTest {

    private IReptileService mockReptileService;
    private ReptileController uut;
    private Reptile reptile;
    private UUID reptileId;

    @BeforeEach
    void beforeEach(){
        this.mockReptileService = Mockito.mock(IReptileService.class);
        this.uut = new ReptileController(mockReptileService);
        this.reptile = new Reptile();
        this.reptileId = UUID.randomUUID();
    }

    @Test
    void testReptileServiceCalledForGetAllReptiles() {
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
