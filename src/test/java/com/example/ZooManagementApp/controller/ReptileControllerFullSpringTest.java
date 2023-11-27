package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.services.IReptileService;
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

@WebMvcTest(ReptileController.class)
@ActiveProfiles("test")
public class ReptileControllerFullSpringTest {

    @MockBean
    private IReptileService iReptileService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testReptileServiceCalledForGetAllReptiles() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/reptiles");
        mockMvc.perform(requestBuilder);
        verify(iReptileService, times(1)).findAllReptiles();
    }

}
