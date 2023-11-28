package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.data.ZooRepository;
import com.example.ZooManagementApp.entities.Amphibian;
import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.entities.Zoo;
import com.example.ZooManagementApp.services.IAmphibianService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(AmphibianController.class)
@ActiveProfiles("test")
public class AmphibianControllerFullSpringTest {

    @MockBean
    IAmphibianService mockAmphibianService;

    @Autowired
    private MockMvc mockMvc;

    Animal amphibian;
    ObjectMapper mapper;
    String json;
    UUID amphibianId;

    @BeforeEach
    void beforeEach() throws JsonProcessingException {
        this.amphibian = new Amphibian();
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.json = mapper.writeValueAsString(amphibian);
        this.amphibianId = UUID.randomUUID();
    }

    @Test
    void testAmphibianServiceCalledForGetAllAmphibians() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/amphibians");
        mockMvc.perform(requestBuilder);
        verify(mockAmphibianService, times(1)).findAllAmphibians();
    }

    @Test
    void test_ServiceCalledFor_GetAmphibianById() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/amphibians/findById/" + amphibianId);
        mockMvc.perform(requestBuilder);

        verify(mockAmphibianService, times(1)).findAmphibianById(amphibianId);
    }

    @Test
    void test_ServiceCalledFor_AddAmphibian() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/amphibians")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockAmphibianService, times(1)).addAmphibian(any(Amphibian.class));
    }

    @Test
    void test_ServiceCalledFor_UpdateAmphibian() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/amphibians")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockAmphibianService, times(1)).updateAmphibian(any(Amphibian.class));
    }

    @Test
    void test_DeleteAmphibian() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/amphibians/findById/" + amphibianId);
        mockMvc.perform(requestBuilder);

        verify(mockAmphibianService, times(1)).deleteAmphibian(amphibianId);
    }
}
