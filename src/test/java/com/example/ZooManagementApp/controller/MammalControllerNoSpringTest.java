package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.services.IMammalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MammalControllerNoSpringTest {

    IMammalService mockMammalService;
    MammalController uut;

    @BeforeEach
    void beforeEach(){
        mockMammalService = Mockito.mock(IMammalService.class);
        uut = new MammalController(mockMammalService);
    }

    @Test
    void getAllZoos() {
        uut.getAllMammals();
        verify(mockMammalService, times(1)).getAllMammals();
    }
}
