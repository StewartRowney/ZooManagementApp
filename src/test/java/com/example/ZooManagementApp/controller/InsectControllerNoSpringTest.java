package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.services.IInsectService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InsectControllerNoSpringTest {
    private final IInsectService mockInsectService = mock(IInsectService.class);
    private final InsectController uut = new InsectController(mockInsectService);

    @Test
    void test_getAllInsects_ValidRequest() {
        uut.getAllInsects();
        verify(mockInsectService, times(1)).findAllInsects();
    }
}