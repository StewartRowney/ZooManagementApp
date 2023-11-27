package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.services.IMammalService;
import com.example.ZooManagementApp.services.IReptileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ReptileControllerNoSpringTest {

    IReptileService iReptileService;
    ReptileController uut;

    @BeforeEach
    void beforeEach(){
        iReptileService = Mockito.mock(IReptileService.class);
        uut = new ReptileController(iReptileService);
    }

    @Test
    void testReptileServiceCalledForGetAllReptiles() {
        uut.getAllReptiles();
        verify(iReptileService, times(1)).findAllReptiles();
    }
}
