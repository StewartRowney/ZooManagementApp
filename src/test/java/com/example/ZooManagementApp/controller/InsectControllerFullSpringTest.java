package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.services.IInsectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(InsectController.class)
@ActiveProfiles("test")
public class InsectControllerFullSpringTest {

    @MockBean
    private IInsectService mockInsectService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_getAllInsects_ValidRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/insects");
        mockMvc.perform(requestBuilder);
        verify(mockInsectService, times(1)).findAllInsects();
    }

    @Test
    void test_getInsectById_ValidRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/insects/" + UUID.randomUUID());
        mockMvc.perform(requestBuilder);
        verify(mockInsectService, times(1)).findInsectById(any(UUID.class));
    }

}
