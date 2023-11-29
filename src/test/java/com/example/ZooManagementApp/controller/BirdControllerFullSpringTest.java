package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Bird;
import com.example.ZooManagementApp.services.IBirdService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

@WebMvcTest(BirdController.class)
@SuppressWarnings("unused")
@ActiveProfiles("test")
public class BirdControllerFullSpringTest {

    @MockBean
    IBirdService mockBirdService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final Bird bird = new Bird();
    private final UUID birdId = UUID.randomUUID();

    @Test
    void test_GetAllBirds_ServiceCalledFor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/birds");
        mockMvc.perform(requestBuilder);
        verify(mockBirdService, times(1)).findAllBirds();
    }

    @Test
    void test_GetBirdById_ServiceCalledFor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/birds/" + birdId);
        mockMvc.perform(requestBuilder);

        verify(mockBirdService, times(1)).findBirdById(birdId);
    }

    @Test
    void test_AddBird_ServiceCalledFor() throws Exception {
        String json = mapper.writeValueAsString(bird);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/birds")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockBirdService, times(1)).addBird(any(Bird.class));
    }

    @Test
    void test_UpdateBird_ServiceCalledFor() throws Exception {
        String json = mapper.writeValueAsString(bird);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/birds")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockBirdService, times(1)).updateBird(any(Bird.class));
    }

    @Test
    void test_DeleteBird_ServiceCalledFor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/birds/" + birdId);
        mockMvc.perform(requestBuilder);

        verify(mockBirdService, times(1)).deleteBird(birdId);
    }
}
