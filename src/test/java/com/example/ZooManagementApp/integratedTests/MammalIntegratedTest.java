package com.example.ZooManagementApp.integratedTests;

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
public class MammalIntegratedTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void test_GetAllMammals_ValidRequest() throws Exception {
        Mammal[] actualMammals = getAllMammals();
        assertEquals(2, actualMammals.length);
    }

    @Test
    void test_GetMammalById_ValidRequest() throws Exception {
        UUID mammalId = UUID.fromString("7a12afea-8b9b-4a7f-94f2-5b57f4e7ffa7");
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/mammals/" + mammalId)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        Mammal mammal = mapper.readValue(contentAsJson, Mammal.class);

        assertAll(
                () -> assertEquals(mammalId, mammal.getId()),
                () -> assertEquals("Jimbo", mammal.getName()),
                () -> assertEquals("Elephant", mammal.getSpeciesName())
        );
    }

    private Mammal[] getAllMammals() throws Exception {
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/mammals")))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        return mapper.readValue(contentAsJson, Mammal[].class);
    }
}
