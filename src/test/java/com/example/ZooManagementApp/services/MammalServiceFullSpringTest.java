package com.example.ZooManagementApp.services;

import com.example.ZooManagementApp.data.IAnimalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class MammalServiceFullSpringTest {

    @MockBean
    IAnimalRepository mockMammalRepository;

    @Autowired
    IMammalService uut;

    @Test
    void testVerifyRepositoryInvokesGetAllMammals() {
        uut.getAllMammals();
        verify(mockMammalRepository, times(1)).findAllMammals();
    }
}
