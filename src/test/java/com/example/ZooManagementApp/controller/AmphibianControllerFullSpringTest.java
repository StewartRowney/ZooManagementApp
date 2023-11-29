package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.entities.Amphibian;
import com.example.ZooManagementApp.entities.Animal;
import com.example.ZooManagementApp.services.IAmphibianService;
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

@WebMvcTest(AmphibianController.class)
@SuppressWarnings("unused")
@ActiveProfiles("test")
public class AmphibianControllerFullSpringTest {

    @MockBean
    IAmphibianService mockAmphibianService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final Amphibian amphibian = new Amphibian();
    private final UUID amphibianId = UUID.randomUUID();

    @Test
    void test_GetAllAmphibians_ServiceCalledFor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/amphibians");
        mockMvc.perform(requestBuilder);
        verify(mockAmphibianService, times(1)).findAllAmphibians();
    }

    @Test
    void test_GetAmphibianById_ServiceCalledFor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/amphibians/" + amphibianId);
        mockMvc.perform(requestBuilder);

        verify(mockAmphibianService, times(1)).findAmphibianById(amphibianId);
    }

    @Test
    void test_AddAmphibian_ServiceCalledFor() throws Exception {
        String json = mapper.writeValueAsString(amphibian);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/amphibians")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockAmphibianService, times(1)).addAmphibian(any(Amphibian.class));
    }

    @Test
    void test_UpdateAmphibian_ServiceCalledFor() throws Exception {
        String json = mapper.writeValueAsString(amphibian);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/amphibians")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockAmphibianService, times(1)).updateAmphibian(any(Amphibian.class));
    }

    @Test
    void test_DeleteAmphibian_ServiceCalledFor() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/amphibians/" + amphibianId);
        mockMvc.perform(requestBuilder);

        verify(mockAmphibianService, times(1)).deleteAmphibian(amphibianId);
    }
}
