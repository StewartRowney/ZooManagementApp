package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Reptile;
import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.services.IReptileService;
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
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(ReptileController.class)
@ActiveProfiles("test")
public class ReptileControllerFullSpringTest {

    @MockBean
    private IReptileService mockReptileService;

    @Autowired
    private MockMvc mockMvc;

    Animal reptile;
    ObjectMapper mapper;
    String json;
    UUID reptileId;

    @BeforeEach
    void beforeEach() throws JsonProcessingException {
        this.reptile = new Reptile();
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.json = mapper.writeValueAsString(reptile);
        this.reptileId = UUID.randomUUID();
    }

    @Test
    void testReptileServiceCalledForGetAllReptiles() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/reptiles");
        mockMvc.perform(requestBuilder);
        verify(mockReptileService, times(1)).findAllReptiles();
    }

    @Test
    void test_ServiceCalledFor_GetReptileById() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/reptiles/findById/" + reptileId);
        mockMvc.perform(requestBuilder);

        verify(mockReptileService, times(1)).findReptileById(reptileId);
    }

    @Test
    void test_ServiceCalledFor_AddReptile() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/reptiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockReptileService, times(1)).addReptile(any(Reptile.class));
    }

    @Test
    void test_ServiceCalledFor_UpdateReptile() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/reptiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockReptileService, times(1)).updateReptile(any(Reptile.class));
    }

    @Test
    void test_DeleteReptile() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/reptiles/findById/" + reptileId);
        mockMvc.perform(requestBuilder);

        verify(mockReptileService, times(1)).deleteReptile(reptileId);
    }
}
