package com.example.zoo.controller;

import com.example.zoo.entities.Insect;
import com.example.zoo.services.IInsectService;
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

@WebMvcTest(InsectController.class)
@SuppressWarnings("unused")
@ActiveProfiles("test")
public class InsectControllerFullSpringTest {

    @MockBean
    IInsectService mockInsectService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final Insect insect = new Insect();
    private final UUID insectId = UUID.randomUUID();

    @Test
    void test_GetAllInsects_ServiceCalledFor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/insects");
        mockMvc.perform(requestBuilder);
        verify(mockInsectService, times(1)).findAllInsects();
    }

    @Test
    void test_GetInsectById_ServiceCalledFor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/insects/" + insectId);
        mockMvc.perform(requestBuilder);

        verify(mockInsectService, times(1)).findInsectById(insectId);
    }

    @Test
    void test_AddInsect_ServiceCalledFor() throws Exception {
        String json = mapper.writeValueAsString(insect);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/insects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockInsectService, times(1)).addInsect(any(Insect.class));
    }

    @Test
    void test_UpdateInsect_ServiceCalledFor() throws Exception {
        String json = mapper.writeValueAsString(insect);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/insects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockInsectService, times(1)).updateInsect(any(Insect.class));
    }

    @Test
    void test_DeleteInsect_ServiceCalledFor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/insects/" + insectId);
        mockMvc.perform(requestBuilder);

        verify(mockInsectService, times(1)).deleteInsect(insectId);
    }
}
