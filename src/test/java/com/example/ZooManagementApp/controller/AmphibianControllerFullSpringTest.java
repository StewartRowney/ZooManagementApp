package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.services.IAmphibianService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(AmphibianController.class)
@ActiveProfiles("test")
public class AmphibianControllerFullSpringTest {

    @MockBean
    IAmphibianService mockAmphibianService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testAmphibianServiceCalledForGetAllAmphibians() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/amphibians");
        mockMvc.perform(requestBuilder);
        verify(mockAmphibianService, times(1)).findAllAmphibians();
    }
}
