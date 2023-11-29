package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Animal;
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
    void testZooServiceCallsFindFishByID() throws Exception {
        UUID id = UUID.randomUUID();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/fish/" + id);
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
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/fish/"+id);
        mockMvc.perform(requestBuilder);
        verify(mockFishService,times(1)).removeFishById(id);
    }

    private Fish createAFish() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String json = "{\n" +
                "    \"id\": \"40ea5519-fcef-4272-b742-e01790ca04c3\",\n" +
                "    \"name\": \"Chester Zoo\",\n" +
                "    \"location\": \"Upton-by-Chester, Cheshire, England\",\n" +
                "    \"capacity\": 27000,\n" +
                "    \"price\": 19,\n" +
                "    \"dateOpened\": \"10-06-1931\"\n" +
                "  }";
        try {
            return mapper.readValue(json, Fish.class);
        } catch (Exception e) {
            return new Fish();
        }
    }

    @Test
    void test_AddFish_ValidRequest() throws Exception {
        Fish fish = new Fish();
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(fish);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/fish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isCreated());
        verify(mockFishService, times(1)).addFish(any(Fish.class));
    }

}
