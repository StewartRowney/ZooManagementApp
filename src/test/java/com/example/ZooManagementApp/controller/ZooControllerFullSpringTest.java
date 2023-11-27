package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.services.IZooService;
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

@WebMvcTest(ZooController.class)
@ActiveProfiles("test")
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

    @Test
    void testZooServiceCallsFindZooByID() throws Exception {
        UUID id = UUID.randomUUID();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/zoos/zooById/" + id);
        mockMvc.perform(requestBuilder);
        verify(mockZooService,times(1)).findZooById(id);
    }

    @Test
    void testZooServiceCallsFindZooByName() throws Exception {
        String name = "zooName";
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/zoos/zooByName/" + name);
        mockMvc.perform(requestBuilder);
        verify(mockZooService,times(1)).findZooByName(name);
    }
}
