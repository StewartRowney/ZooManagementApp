package com.example.ZooManagementApp.integratedTests;

import com.example.ZooManagementApp.entities.Amphibian;
import com.example.ZooManagementApp.entities.Zoo;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
public class AmphibianIntegratedTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void test_GetAllAmphibians_ValidRequest() throws Exception {
        Amphibian[] actualAmphibians = getAllAmphibians();
        assertEquals(2, actualAmphibians.length);
    }

    @Test
    void testAddAmphibian() throws Exception {

        int numberOfAmphibiansBeforeAdd = getAllAmphibians().length;

        Amphibian amphibian = new Amphibian(new Zoo(), "Terry", "Snail", LocalDate.of(1990,12,18),
                "Anywhere", "Stone cold killer", "Humans", "Has killed multiple zookeepers", false, false);
        Amphibian actualamphibian = addAmphibian(amphibian);
        int numberOfAmphibiansAfterAdd = getAllAmphibians().length;

        assertAll(
                () -> assertEquals(amphibian.getName(), actualamphibian.getName()),
                () -> assertEquals(amphibian.getSpeciesName(), actualamphibian.getSpeciesName()),
                () -> assertEquals(amphibian.getId(), actualamphibian.getId()),
                () -> assertEquals(numberOfAmphibiansBeforeAdd + 1, numberOfAmphibiansAfterAdd)
        );

    }

    private Amphibian[] getAllAmphibians() throws Exception {
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/amphibians")))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        return mapper.readValue(contentAsJson, Amphibian[].class);
    }

    private Amphibian addAmphibian(Amphibian amphibian) throws Exception {
        String json = mapper.writeValueAsString(amphibian);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/amphibians")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = (mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn());

        String contentAsJson = result.getResponse().getContentAsString();
        return mapper.readValue(contentAsJson, Amphibian.class);
    }
}
