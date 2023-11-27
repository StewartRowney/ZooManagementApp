package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.services.IZooService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(ZooController.class)
public class ZooControllerFullSpringTest {

    @MockBean
    IZooService mockZooService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testZooServiceCallsFindAll() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/zoos");
        mockMvc.perform(requestBuilder);
        verify(mockZooService,times(1)).findAllZoos();
    }
}
