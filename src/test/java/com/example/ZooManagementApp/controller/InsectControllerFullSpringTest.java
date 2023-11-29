package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Insect;
import com.example.ZooManagementApp.entities.Mammal;
import com.example.ZooManagementApp.services.IInsectService;
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
@ActiveProfiles("test")
public class InsectControllerFullSpringTest {

    @MockBean
    private IInsectService mockInsectService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void test_getAllInsects_ValidRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/insects");
        mockMvc.perform(requestBuilder);
        verify(mockInsectService, times(1)).findAllInsects();
    }

    @Test
    void test_getInsectById_ValidRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/insects/" + UUID.randomUUID());
        mockMvc.perform(requestBuilder);
        verify(mockInsectService, times(1)).findInsectById(any(UUID.class));
    }

    @Test
    void test_addInsect_ValidRequest() throws Exception {
        String json = mapper.writeValueAsString(new Insect());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/insects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockInsectService, times(1)).addInsect(any(Insect.class));
    }

    @Test
    void test_UpdateInsect_ValidRequest() throws Exception {
        String json = mapper.writeValueAsString(new Insect());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/insects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isCreated());
        verify(mockInsectService, times(1)).updateInsect(any(Insect.class));
    }

    @Test
    void test_DeleteById_ValidRequest() throws Exception {
        UUID insectId = UUID.randomUUID();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/insects/" + insectId);
        mockMvc.perform(requestBuilder);
        verify(mockInsectService,times(1)).deleteInsectById(any(UUID.class));
    }

}
