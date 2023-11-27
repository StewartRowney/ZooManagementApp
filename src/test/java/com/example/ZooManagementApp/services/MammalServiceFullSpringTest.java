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

    @MockBean
    I mockPersonService;

    @Autowired
    MessageService uut;

    @Test
    void test_GetAllMessages_ValidRequest() {
        uut.getAllMessages();
        verify(mockMammalRepository, times(1)).findAll();
    }
}
