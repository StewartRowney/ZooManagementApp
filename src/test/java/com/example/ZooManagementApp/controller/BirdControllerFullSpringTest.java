package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Bird;
import com.example.ZooManagementApp.entities.Zoo;
import com.example.ZooManagementApp.services.IBirdService;
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

@WebMvcTest(BirdController.class)
@ActiveProfiles("test")
public class BirdControllerFullSpringTest {

    @MockBean
    private IBirdService mockBirdService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_findAllBirds_ValidRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/birds");
        mockMvc.perform(requestBuilder);
        verify(mockBirdService, times(1)).findAllBirds();
    }

    @Test
    void test_FindBirdByID_ValidRequest() throws Exception {
        UUID id = UUID.randomUUID();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/birds/findById/" + id);
        mockMvc.perform(requestBuilder);
        verify(mockBirdService,times(1)).findBirdById(id);
    }


    @Test
    void test_AddingANewBird_ValidRequest() throws Exception {
        Bird bird = new Bird();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(bird);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/birds")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockBirdService, times(1)).addNewBird(any(Bird.class));
    }

    @Test
    void test_UpdateABird_ValidRequest() throws Exception {
        Bird bird = new Bird();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(bird);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/birds")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockBirdService, times(1)).updateBirdWithPut(any(Bird.class));
    }


    @Test
    void testBirdServiceCallsRemoveBird() throws Exception {
        UUID id = UUID.randomUUID();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/birds/deleteBird/"+id);
        mockMvc.perform(requestBuilder);
        verify(mockBirdService,times(1)).removeBirdById(id);
    }
}
