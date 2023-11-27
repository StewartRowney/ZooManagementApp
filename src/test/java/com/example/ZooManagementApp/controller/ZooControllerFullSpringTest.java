package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Zoo;
import com.example.ZooManagementApp.services.IZooService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/zoos/findById/" + id);
        mockMvc.perform(requestBuilder);
        verify(mockZooService,times(1)).findZooById(id);
    }

    @Test
    void testZooServiceCallsFindZooByName() throws Exception {
        String name = "zooName";
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/zoos/findByName/" + name);
        mockMvc.perform(requestBuilder);
        verify(mockZooService,times(1)).findZooByName(name);
    }

    @Test
    void testAddingANewZoo() throws Exception {
        Zoo zoo = new Zoo();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(zoo);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/zoos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockZooService, times(1)).addNewZoo(any(Zoo.class));
    }
}
