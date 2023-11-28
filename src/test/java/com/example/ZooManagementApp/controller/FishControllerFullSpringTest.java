package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.entities.Fish;
import com.example.ZooManagementApp.services.IFishService;
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
    @Test
    void test_UpdateAFish_ValidRequest() throws Exception {
        Fish fish = new Fish();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(fish);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/fish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockFishService, times(1)).updateFishWithPut(any(Fish.class));
    }

    @Test
    void testZooServiceCallsRemoveZoo() throws Exception {
        UUID id = createAFish().getId();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/zoos/deleteZoo/"+id);
        mockMvc.perform(requestBuilder);
        verify(mockFishService,times(1)).removeFishById(id);
    }

    private Animal createAFish() {
        return null;
    }


}
