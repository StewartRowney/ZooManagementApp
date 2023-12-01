package com.example.zoo.integratedTests;

import com.example.zoo.entities.Animal;
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

import java.util.ArrayList;
import java.util.List;
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
class AnimalIntegratedTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void test_GetAllAnimals_ValidRequest() throws Exception {
        Animal[] actualAnimals = getAllAnimals();
        assertEquals(12, actualAnimals.length);
    }

    @Test
    void test_GetAnimalById_ValidRequest() throws Exception {
        UUID animalId = UUID.fromString("60229efa-5978-4cf0-b2f8-76a690ef32b6");
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/animals/findById/" + animalId)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        Animal animal = mapper.readValue(contentAsJson, Animal.class);

        assertAll(
                () -> assertEquals(animalId, animal.getId()),
                () -> assertEquals("Jerry", animal.getName()),
                () -> assertEquals("Cane Toad", animal.getSpeciesName())
        );
    }

    @Test
    void test_FindAListOfAnimals_ValidRequest() throws Exception {

        String json = createIdList();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/animals/findByIds")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = (mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn());

        String contentAsJson = result.getResponse().getContentAsString();
        Animal animalResult[] = mapper.readValue(contentAsJson, Animal[].class);


        assertAll(
                () -> assertEquals("Ayush", animalResult[0].getName()),
                () -> assertEquals("Jimbo", animalResult[1].getName())
        );
    }

    private Animal[] getAllAnimals() throws Exception {
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/animals")))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        return mapper.readValue(contentAsJson, Animal[].class);
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

    public String createIdList() throws JsonProcessingException {
        List<UUID> idList = new ArrayList<>();
        idList.add(UUID.fromString("7a12afea-8b9b-4a7f-94f2-5b57f4e7ffa7"));
        idList.add(UUID.fromString("2479f54f-b8c4-449f-a54c-31fd1d6074dc"));
        idList.add(UUID.fromString("9b2d9232-9385-4707-965f-e5a90cbcfc88"));
        return mapper.writeValueAsString(idList);
    }
}
