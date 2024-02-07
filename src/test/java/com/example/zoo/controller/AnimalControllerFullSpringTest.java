package com.example.zoo.controller;

import com.example.zoo.entities.Animal;
import com.example.zoo.services.IAnimalService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(AnimalController.class)
@ActiveProfiles("test")
class AnimalControllerFullSpringTest {

    @MockBean
    private IAnimalService mockAnimalService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final Animal animal = new Animal();
    private final UUID animalId = UUID.randomUUID();

    @Test
    void test_getAllAnimals_ServiceCalledFor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/animals");
        mockMvc.perform(requestBuilder);
        verify(mockAnimalService, times(1)).findAllAnimals();
    }

    @Test
    void test_GetAnimalByID_ServiceCalledFor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/animals/findById/" + animalId);
        mockMvc.perform(requestBuilder);
        verify(mockAnimalService,times(1)).findAnimalById(animalId);
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

    @Test
    void test_deleteAnimalsByIds_ValidRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/animals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createIdList());

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(mockAnimalService, times(1)).deleteAnimalsByIds(any());
    }

    public String createIdList() throws JsonProcessingException {
        List<UUID> idList = new ArrayList<>();
        idList.add(UUID.randomUUID());
        idList.add(UUID.randomUUID());
        idList.add(UUID.randomUUID());
        return mapper.writeValueAsString(idList);
    }
}
