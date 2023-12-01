package com.example.zoo.integratedTests;

import com.example.zoo.entities.Mammal;
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
class MammalIntegratedTest {

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

    @Test
    void test_AddMammal_ValidRequest() throws Exception {
        int numberOfMammalsBeforeAdd = getAllMammals().length;
        Mammal mammal = new Mammal();
        mammal.setName("Test");
        mammal.setSpeciesName("testAnimal");
        mammal.setZoo(getTestZoo());
        String json = mapper.writeValueAsString(mammal);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/mammals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = (mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn());

        String contentAsJson = result.getResponse().getContentAsString();
        Mammal mammalResult = mapper.readValue(contentAsJson, Mammal.class);

        int numberOfMammalsAfterAdd = getAllMammals().length;

        assertAll(
                () -> assertEquals(mammal.getName(), mammalResult.getName()),
                () -> assertEquals(mammal.getSpeciesName(), mammalResult.getSpeciesName()),
                () -> assertEquals(numberOfMammalsBeforeAdd + 1, numberOfMammalsAfterAdd)
        );
    }

    @Test
    void test_UpdateMammal_ValidRequest() throws Exception {
        int numberOfMammalsBeforeUpdate = getAllMammals().length;
        Mammal mammal = getTestMammal();
        String json = mapper.writeValueAsString(mammal);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/mammals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = (mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn());

        String contentAsJson = result.getResponse().getContentAsString();
        Mammal mammalResult = mapper.readValue(contentAsJson, Mammal.class);

        int numberOfMammalsAfterUpdate = getAllMammals().length;

        assertAll(
                () -> assertEquals(mammal.getName(), mammalResult.getName()),
                () -> assertEquals(mammal.getSpeciesName(), mammalResult.getSpeciesName()),
                () -> assertEquals(numberOfMammalsBeforeUpdate, numberOfMammalsAfterUpdate)
        );
    }

    @Test
    void test_DeleteMammalById_ValidRequest() throws Exception {
        int numberOfMammalsBeforeDelete = getAllMammals().length;
        UUID mammalId = getTestMammal().getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/mammals/" + mammalId));
        int numberOfMammalsAfterDelete = getAllMammals().length;

        assertEquals(numberOfMammalsBeforeDelete - 1, numberOfMammalsAfterDelete);
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

    private Mammal getTestMammal() {
        String json = """
                {
                  "id": "2479f54f-b8c4-449f-a54c-31fd1d6074dc",
                  "name": "string",
                  "speciesName": "string",
                  "birthDate": "2021-11-28",
                  "habitat": "string",
                  "behaviour": "string",
                  "foodType": "string",
                  "extraInformation": "string",
                  "hasFur": true,
                  "hasFins": true,
                  "hasHooves": true
                }""";

        try {
            Mammal mammal = mapper.readValue(json, Mammal.class);
            mammal.setZoo(getTestZoo());
            return mammal;
        } catch (JsonProcessingException e) {
            return new Mammal();
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
                    "dateOpened": "1999-05-12"
                  }""";
        try {
            return mapper.readValue(json, Zoo.class);
        } catch (JsonProcessingException e) {
            return new Zoo();
        }
    }
}
