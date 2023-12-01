package com.example.zoo.integratedTests;

import com.example.zoo.entities.Fish;
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
class FishIntegratedTest {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void test_GetAllFish_ValidRequest() throws Exception {
        Fish[] actualFish = getAllFish();
        assertEquals(2, actualFish.length);
    }

    @Test
    void test_GetFishById_ValidRequest() throws Exception {
        UUID fishId = UUID.fromString("8bb33239-24df-473e-9d35-2ca6a9bdd9ca");
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/fish/" + fishId)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        Fish fish = mapper.readValue(contentAsJson, Fish.class);

        assertAll(
                () -> assertEquals(fishId, fish.getId()),
                () -> assertEquals("Diane", fish.getName()),
                () -> assertEquals("Hammerhead Shark", fish.getSpeciesName())
        );
    }

    @Test
    void test_AddFish_ValidRequest() throws Exception {
        int numberOfFishBeforeAdd = getAllFish().length;
        Fish fish = new Fish();
        fish.setName("Test");
        fish.setSpeciesName("testAnimal");
        fish.setZoo(getTestZoo());
        String json = mapper.writeValueAsString(fish);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/fish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = (mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn());

        String contentAsJson = result.getResponse().getContentAsString();
        Fish fishResult = mapper.readValue(contentAsJson, Fish.class);

        int numberOfFishAfterAdd = getAllFish().length;

        assertAll(
                () -> assertEquals(fish.getName(), fishResult.getName()),
                () -> assertEquals(fish.getSpeciesName(), fishResult.getSpeciesName()),
                () -> assertEquals(numberOfFishBeforeAdd + 1, numberOfFishAfterAdd)
        );
    }

    @Test
    void test_UpdateFish_ValidRequest() throws Exception {
        int numberOfFishBeforeUpdate = getAllFish().length;
        Fish fish = getTestFish();
        String json = mapper.writeValueAsString(fish);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/fish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = (mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn());

        String contentAsJson = result.getResponse().getContentAsString();
        Fish fishResult = mapper.readValue(contentAsJson, Fish.class);

        int numberOfFishAfterUpdate = getAllFish().length;

        assertAll(
                () -> assertEquals(fish.getName(), fishResult.getName()),
                () -> assertEquals(fish.getSpeciesName(), fishResult.getSpeciesName()),
                () -> assertEquals(numberOfFishBeforeUpdate, numberOfFishAfterUpdate)
        );
    }
    @Test
    void test_DeleteFish_ValidRequest() throws Exception {
        int numberOfFishBeforeDelete = getAllFish().length;
        UUID fishId = getTestFish().getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/fish/" + fishId));
        int numberOfFishAfterDelete = getAllFish().length;

        assertEquals(numberOfFishBeforeDelete - 1, numberOfFishAfterDelete);
    }

    private Fish[] getAllFish() throws Exception {
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/fish")))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        return mapper.readValue(contentAsJson, Fish[].class);
    }

    private Fish getTestFish() {
        String json = """
                {
                  "id": "b275712e-e0c6-45ed-8a1d-e38fd0753eb9",
                  "name": "string",
                  "speciesName": "string",
                  "birthDate": "2023-11-28",
                  "habitat": "string",
                  "behaviour": "string",
                  "foodType": "string",
                  "extraInformation": "string",
                  "canDischargeElectricity": true,
                  "bioluminiscent": true
                }""";

        try {
            Fish fish = mapper.readValue(json, Fish.class);
            fish.setZoo(getTestZoo());
            return fish;
        } catch (JsonProcessingException e) {
            return new Fish();
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
