package com.example.ZooManagementApp.integratedTests;

import com.example.ZooManagementApp.entities.Insect;
import com.example.ZooManagementApp.entities.Mammal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql("classpath:test-data.sql")
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {"spring.sql.init.mode=never"})
@ActiveProfiles("test")
public class InsectIntegratedTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void test_GetAllInsects_ValidRequest() throws Exception {
        Insect[] actualInsects = getAllInsects();
        assertEquals(2, actualInsects.length);
    }

    @Test
    void test_GetInsectById_ValidRequest() throws Exception {
        UUID insectId = UUID.fromString("26ba3474-846b-4a6d-8f43-6df6f981dbe6");
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/insects/" + insectId)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        Insect insect = mapper.readValue(contentAsJson, Insect.class);

        assertAll(
                () -> assertEquals(insectId, insect.getId()),
                () -> assertEquals("Terry", insect.getName()),
                () -> assertEquals("Worm", insect.getSpeciesName())
        );
    }

    private Insect[] getAllInsects() throws Exception {
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/insects")))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        return mapper.readValue(contentAsJson, Insect[].class);
    }
}
