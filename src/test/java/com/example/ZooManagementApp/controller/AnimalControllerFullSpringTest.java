package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Amphibian;
import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.entities.Zoo;
import com.example.ZooManagementApp.services.IAnimalService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(AnimalController.class)
@ActiveProfiles("test")
public class AnimalControllerFullSpringTest {

    @MockBean
    private IAnimalService mockAnimalService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    void test_getAllAnimals_ValidRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/animals");
        mockMvc.perform(requestBuilder);
        verify(mockAnimalService, times(1)).findAllAnimals();
    }

    @Test
    void testZooServiceCallsFindZooByID() throws Exception {
        UUID id = UUID.randomUUID();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/animals/findById/" + id);
        mockMvc.perform(requestBuilder);
        verify(mockAnimalService,times(1)).findAnimalById(id);
    }

    @Test
    void test_postAllAnimalsByIds_ValidRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/animals/findByIds")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createIdList())
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(mockAnimalService, times(1)).findAnimalListById(any());
    }

    public String createIdList() throws JsonProcessingException {
        List<UUID> idList = new ArrayList<>();
        idList.add(UUID.randomUUID());
        idList.add(UUID.randomUUID());
        idList.add(UUID.randomUUID());
        return mapper.writeValueAsString(idList);
    }
}
