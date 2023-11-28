package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.services.IFishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.Mockito.*;

@WebMvcTest(FishController.class)
@ActiveProfiles("test")
public class FishControllerFullSpringTest {

    @MockBean
    private IFishService mockFishService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testFishServiceCalledForGetAllFish() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/fish");
        mockMvc.perform(requestBuilder);
        verify(mockFishService, times(1)).findAllFish();
    }

    @Test
    void testZooServiceCallsFindZooByID() throws Exception {
        UUID id = UUID.randomUUID();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/fish/findById/" + id);
        mockMvc.perform(requestBuilder);
        verify(mockFishService,times(1)).findFishById(id);
    }

}
