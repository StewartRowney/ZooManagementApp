package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.services.IBirdService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(BirdController.class)
@ActiveProfiles("test")
public class BirdControllerFullSpringTest {

    @MockBean
    private IBirdService mockBirdService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testBirdServiceCalledForGetAllBirds() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/birds");
        mockMvc.perform(requestBuilder);
        verify(mockBirdService, times(1)).findAllBirds();
    }
}
