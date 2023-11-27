package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.services.IAnimalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(AnimalController.class)
@ActiveProfiles("test")
public class AnimalControllerFullSpringTest {

    @MockBean
    private IAnimalService mockAnimalService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_getAllAnimals_ValidRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/animals");
        mockMvc.perform(requestBuilder);
        verify(mockAnimalService, times(1)).findAllAnimals();
    }

    @Test
    void testZooServiceCallsFindZooByID() throws Exception {
        UUID id = UUID.randomUUID();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/animals/findById/" + id);
        mockMvc.perform(requestBuilder);
        verify(mockAnimalService,times(1)).findAnimalById(id);
    }

}
