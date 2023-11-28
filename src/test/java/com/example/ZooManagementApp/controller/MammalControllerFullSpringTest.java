package com.example.ZooManagementApp.controller;

import com.example.ZooManagementApp.services.IMammalService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(MammalController.class)
public class MammalControllerFullSpringTest {

    @MockBean
    IMammalService mammalService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testMammalServiceCalledForGetAllMammals() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/mammals");
        mockMvc.perform(requestBuilder);
        verify(mammalService,times(1)).findAllMammals();
    }
}
