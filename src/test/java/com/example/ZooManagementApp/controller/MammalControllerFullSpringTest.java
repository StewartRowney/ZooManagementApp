package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.services.IMammalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(MammalController.class)
public class MammalControllerFullSpringTest {

    @MockBean
    IMammalService mockMammalService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void test_GetAllMammals_ValidRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/mammals");
        mockMvc.perform(requestBuilder);
        verify(mockMammalService,times(1)).findAllMammals();
    }

    @Test
    void test_GetMammalById_ValidRequest() throws Exception {
        UUID mammalId = UUID.randomUUID();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/mammals/" + mammalId);
        mockMvc.perform(requestBuilder);
        verify(mockMammalService, times(1)).findMammalById(mammalId);
    }
}
