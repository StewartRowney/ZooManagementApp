package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Mammal;
import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.services.IMammalService;
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

@WebMvcTest(MammalController.class)
@SuppressWarnings("unused")
@ActiveProfiles("test")
public class MammalControllerFullSpringTest {

    @MockBean
    IMammalService mockMammalService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final Mammal mammal = new Mammal();
    private final UUID mammalId = UUID.randomUUID();

    @Test
    void test_GetAllMammals_ServiceCalledFor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/mammals");
        mockMvc.perform(requestBuilder);
        verify(mockMammalService, times(1)).findAllMammals();
    }

    @Test
    void test_GetMammalById_ServiceCalledFor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/mammals/" + mammalId);
        mockMvc.perform(requestBuilder);

        verify(mockMammalService, times(1)).findMammalById(mammalId);
    }

    @Test
    void test_AddMammal_ServiceCalledFor() throws Exception {
        String json = mapper.writeValueAsString(mammal);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/mammals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockMammalService, times(1)).addMammal(any(Mammal.class));
    }

    @Test
    void test_UpdateMammal_ServiceCalledFor() throws Exception {
        String json = mapper.writeValueAsString(mammal);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/mammals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockMammalService, times(1)).updateMammal(any(Mammal.class));
    }

    @Test
    void test_DeleteMammal_ServiceCalledFor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/mammals/" + mammalId);
        mockMvc.perform(requestBuilder);

        verify(mockMammalService, times(1)).deleteMammal(mammalId);
    }
}
