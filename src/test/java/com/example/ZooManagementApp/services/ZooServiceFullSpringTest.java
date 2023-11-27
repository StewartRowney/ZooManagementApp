package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.ZooRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;

@SpringBootTest
class ZooServiceFullSpringTest {

    @MockBean
    ZooRepository mockZooRepo;

    @Autowired
    ZooService uut;

    @Test
    void verifyIfRepositoryInvokesFindAll() {
        uut.findAllZoos();
        verify(mockZooRepo,times(1)).findAll();
    }
}