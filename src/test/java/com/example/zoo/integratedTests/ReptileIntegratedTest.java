package com.example.zoo.integratedTests;

import com.example.zoo.entities.Reptile;
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
public class ReptileIntegratedTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void test_GetAllReptiles_ValidRequest() throws Exception {
        Reptile[] actualReptiles = getAllReptiles();
        assertEquals(2, actualReptiles.length);
    }

    @Test
    void test_GetReptileById_ValidRequest() throws Exception {
        UUID reptileId = UUID.fromString("9b2d9232-9385-4707-965f-e5a90cbcfc88");
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/reptiles/" + reptileId)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        Reptile reptile = mapper.readValue(contentAsJson, Reptile.class);

        assertAll(
                () -> assertEquals(reptileId, reptile.getId()),
                () -> assertEquals("Sally", reptile.getName()),
                () -> assertEquals("Python", reptile.getSpeciesName())
        );
    }

    @Test
    void test_AddReptile_ValidRequest() throws Exception {
        int numberOfReptilesBeforeAdd = getAllReptiles().length;
        Reptile reptile = new Reptile();
        reptile.setName("Test");
        reptile.setSpeciesName("testAnimal");
        reptile.setZoo(getTestZoo());
        String json = mapper.writeValueAsString(reptile);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/reptiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = (mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn());

        String contentAsJson = result.getResponse().getContentAsString();
        Reptile reptileResult = mapper.readValue(contentAsJson, Reptile.class);

        int numberOfReptilesAfterAdd = getAllReptiles().length;

        assertAll(
                () -> assertEquals(reptile.getName(), reptileResult.getName()),
                () -> assertEquals(reptile.getSpeciesName(), reptileResult.getSpeciesName()),
                () -> assertEquals(numberOfReptilesBeforeAdd + 1, numberOfReptilesAfterAdd)
        );
    }

    @Test
    void test_UpdateReptile_ValidRequest() throws Exception {
        int numberOfReptilesBeforeUpdate = getAllReptiles().length;
        Reptile reptile = getTestReptile();
        String json = mapper.writeValueAsString(reptile);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/reptiles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = (mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn());

        String contentAsJson = result.getResponse().getContentAsString();
        Reptile reptileResult = mapper.readValue(contentAsJson, Reptile.class);

        int numberOfReptilesAfterUpdate = getAllReptiles().length;

        assertAll(
                () -> assertEquals(reptile.getName(), reptileResult.getName()),
                () -> assertEquals(reptile.getSpeciesName(), reptileResult.getSpeciesName()),
                () -> assertEquals(numberOfReptilesBeforeUpdate, numberOfReptilesAfterUpdate)
        );
    }

    @Test
    void test_DeleteReptileById_ValidRequest() throws Exception {
        int numberOfReptilesBeforeDelete = getAllReptiles().length;
        UUID reptileId = getTestReptile().getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/reptiles/" + reptileId));
        int numberOfReptilesAfterDelete = getAllReptiles().length;

        assertEquals(numberOfReptilesBeforeDelete - 1, numberOfReptilesAfterDelete);
    }

    private Reptile[] getAllReptiles() throws Exception {
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/reptiles")))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        return mapper.readValue(contentAsJson, Reptile[].class);
    }

    private Reptile getTestReptile() {
        String json = """
                {
                  "id": "9b2d9232-9385-4707-965f-e5a90cbcfc88",
                  "name": "string",
                  "speciesName": "string",
                  "birthDate": "28-11-2021",
                  "habitat": "string",
                  "behaviour": "string",
                  "foodType": "string",
                  "extraInformation": "string",
                  "hasShell": false,
                  "isColdBlooded": false,
                  "hasLegs": true
                }""";

        try {
            Reptile reptile = mapper.readValue(json, Reptile.class);
            reptile.setZoo(getTestZoo());
            return reptile;
        } catch (JsonProcessingException e) {
            return new Reptile();
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
