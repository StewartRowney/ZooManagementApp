package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Reptile;
import com.example.ZooManagementApp.services.IReptileService;
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

@WebMvcTest(ReptileController.class)
@SuppressWarnings("unused")
@ActiveProfiles("test")
public class ReptileControllerFullSpringTest {

    @MockBean
    IReptileService mockReptileService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final Reptile reptile = new Reptile();
    private final UUID reptileId = UUID.randomUUID();

    @Test
    void test_GetAllReptiles_ServiceCalledFor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/reptiles");
        mockMvc.perform(requestBuilder);
        verify(mockReptileService, times(1)).findAllReptiles();
    }

    @Test
    void test_GetReptileById_ServiceCalledFor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/reptiles/" + reptileId);
        mockMvc.perform(requestBuilder);

        verify(mockReptileService, times(1)).findReptileById(reptileId);
    }

    @Test
    void test_AddReptile_ServiceCalledFor() throws Exception {
        String json = mapper.writeValueAsString(reptile);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/reptiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockReptileService, times(1)).addReptile(any(Reptile.class));
    }

    @Test
    void test_UpdateReptile_ServiceCalledFor() throws Exception {
        String json = mapper.writeValueAsString(reptile);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/reptiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockReptileService, times(1)).updateReptile(any(Reptile.class));
    }

    @Test
    void test_DeleteReptile_ServiceCalledFor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/reptiles/" + reptileId);
        mockMvc.perform(requestBuilder);

        verify(mockReptileService, times(1)).deleteReptile(reptileId);
    }
}
