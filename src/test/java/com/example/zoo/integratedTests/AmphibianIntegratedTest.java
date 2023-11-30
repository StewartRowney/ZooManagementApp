package com.example.zoo.integratedTests;

import com.example.zoo.entities.Amphibian;
import com.example.zoo.entities.Zoo;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    void test_GetAmphibianById_ValidRequest() throws Exception {
        UUID amphibianId = UUID.fromString("60229efa-5978-4cf0-b2f8-76a690ef32b6");
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/amphibians/" + amphibianId)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        Amphibian amphibian = mapper.readValue(contentAsJson, Amphibian.class);

        assertAll(
                () -> assertEquals(amphibianId, amphibian.getId()),
                () -> assertEquals("Jerry", amphibian.getName()),
                () -> assertEquals("Cane Toad", amphibian.getSpeciesName())
        );
    }

    @Test
    void test_AddAmphibian_ValidRequest() throws Exception {
        int numberOfAmphibiansBeforeAdd = getAllAmphibians().length;
        Amphibian amphibian = new Amphibian();
        amphibian.setName("Test");
        amphibian.setSpeciesName("testAnimal");
        amphibian.setZoo(getTestZoo());
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
        Amphibian amphibianResult = mapper.readValue(contentAsJson, Amphibian.class);

        int numberOfAmphibiansAfterAdd = getAllAmphibians().length;

        assertAll(
                () -> assertEquals(amphibian.getName(), amphibianResult.getName()),
                () -> assertEquals(amphibian.getSpeciesName(), amphibianResult.getSpeciesName()),
                () -> assertEquals(numberOfAmphibiansBeforeAdd + 1, numberOfAmphibiansAfterAdd)
        );
    }

    @Test
    void test_UpdateAmphibian_ValidRequest() throws Exception {
        int numberOfAmphibiansBeforeUpdate = getAllAmphibians().length;
        Amphibian amphibian = getTestAmphibian();
        String json = mapper.writeValueAsString(amphibian);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/amphibians")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = (mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn());

        String contentAsJson = result.getResponse().getContentAsString();
        Amphibian amphibianResult = mapper.readValue(contentAsJson, Amphibian.class);

        int numberOfAmphibiansAfterUpdate = getAllAmphibians().length;

        assertAll(
                () -> assertEquals(amphibian.getName(), amphibianResult.getName()),
                () -> assertEquals(amphibian.getSpeciesName(), amphibianResult.getSpeciesName()),
                () -> assertEquals(numberOfAmphibiansBeforeUpdate, numberOfAmphibiansAfterUpdate)
        );
    }

    @Test
    void test_DeleteAmphibian_ValidRequest() throws Exception {
        int numberOfAmphibiansBeforeDelete = getAllAmphibians().length;
        UUID amphibianId = getTestAmphibian().getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/amphibians/" + amphibianId));
        int numberOfAmphibiansAfterDelete = getAllAmphibians().length;

        assertEquals(numberOfAmphibiansBeforeDelete - 1, numberOfAmphibiansAfterDelete);
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

    private Amphibian getTestAmphibian() {
        String json = """
                {
                  "id": "60229efa-5978-4cf0-b2f8-76a690ef32b6",
                  "name": "string",
                  "speciesName": "string",
                  "birthDate": "28-11-2021",
                  "habitat": "string",
                  "behaviour": "string",
                  "foodType": "string",
                  "extraInformation": "string",
                  "isPoisonous": true,
                  "makesNoise": true
                }""";

        try {
            Amphibian amphibian = mapper.readValue(json, Amphibian.class);
            amphibian.setZoo(getTestZoo());
            return amphibian;
        } catch (JsonProcessingException e) {
            return new Amphibian();
        }
    }

    private Zoo getTestZoo() {
        String json = """
                 {
                    "id": "40ea5519-fcef-4272-b742-e01790ca04c3",
                    "name": "string",
                    "location": "string",
                    "capacity": 0,
                    "price": 0,
                    "dateOpened": "12-05-1999"
                  }""";
        try {
            return mapper.readValue(json, Zoo.class);
        } catch (JsonProcessingException e) {
            return new Zoo();
        }
    }
}
