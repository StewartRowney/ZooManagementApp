package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Fish;
import com.example.ZooManagementApp.services.IFishService;
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

@WebMvcTest(FishController.class)
@SuppressWarnings("unused")
@ActiveProfiles("test")
public class FishControllerFullSpringTest {

    @MockBean
    IFishService mockFishService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final Fish fish = new Fish();
    private final UUID fishId = UUID.randomUUID();

    @Test
    void test_GetAllFishs_ServiceCalledFor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/fish");
        mockMvc.perform(requestBuilder);
        verify(mockFishService, times(1)).findAllFish();
    }

    @Test
    void test_GetFishById_ServiceCalledFor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/fish/" + fishId);
        mockMvc.perform(requestBuilder);

        verify(mockFishService, times(1)).findFishById(fishId);
    }

    @Test
    void test_AddFish_ServiceCalledFor() throws Exception {
        String json = mapper.writeValueAsString(fish);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/fish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockFishService, times(1)).addFish(any(Fish.class));
    }

    @Test
    void test_UpdateFish_ServiceCalledFor() throws Exception {
        String json = mapper.writeValueAsString(fish);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/fish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockFishService, times(1)).updateFish(any(Fish.class));
    }

    @Test
    void test_DeleteFish_ServiceCalledFor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/fish/" + fishId);
        mockMvc.perform(requestBuilder);

        verify(mockFishService, times(1)).deleteFish(fishId);
    }
}
