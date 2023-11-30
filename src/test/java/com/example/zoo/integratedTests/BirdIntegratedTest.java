package com.example.zoo.integratedTests;

import com.example.zoo.entities.Bird;
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
public class BirdIntegratedTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void test_GetAllBirds_ValidRequest() throws Exception {
        Bird[] actualBirds = getAllBirds();
        assertEquals(2, actualBirds.length);
    }

    @Test
    void test_GetBirdById_ValidRequest() throws Exception {
        UUID birdId = UUID.fromString("952a60c2-e4ad-422c-bb55-7f1aad97c15d");
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/birds/" + birdId)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        Bird bird = mapper.readValue(contentAsJson, Bird.class);

        assertAll(
                () -> assertEquals(birdId, bird.getId()),
                () -> assertEquals("Stew", bird.getName()),
                () -> assertEquals("Cockatoo", bird.getSpeciesName())
        );
    }

    @Test
    void test_AddBird_ValidRequest() throws Exception {
        int numberOfBirdsBeforeAdd = getAllBirds().length;
        Bird bird = new Bird();
        bird.setName("Test");
        bird.setSpeciesName("testAnimal");
        bird.setZoo(getTestZoo());
        String json = mapper.writeValueAsString(bird);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/birds")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = (mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn());

        String contentAsJson = result.getResponse().getContentAsString();
        Bird birdResult = mapper.readValue(contentAsJson, Bird.class);

        int numberOfBirdsAfterAdd = getAllBirds().length;

        assertAll(
                () -> assertEquals(bird.getName(), birdResult.getName()),
                () -> assertEquals(bird.getSpeciesName(), birdResult.getSpeciesName()),
                () -> assertEquals(numberOfBirdsBeforeAdd + 1, numberOfBirdsAfterAdd)
        );
    }

    @Test
    void test_UpdateBird_ValidRequest() throws Exception {
        int numberOfBirdsBeforeAdd = getAllBirds().length;
        Bird bird = getTestBird();
        String json = mapper.writeValueAsString(bird);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/birds")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = (mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn());

        String contentAsJson = result.getResponse().getContentAsString();
        Bird bird1 = mapper.readValue(contentAsJson, Bird.class);

        int numberOfBirdsAfterAdd = getAllBirds().length;

        assertAll(
                () -> assertEquals(bird.getName(), bird1.getName()),
                () -> assertEquals(bird.getSpeciesName(), bird1.getSpeciesName()),
                () -> assertEquals(numberOfBirdsBeforeAdd, numberOfBirdsAfterAdd)
        );
    }

    @Test
    void test_DeleteBird_ValidRequest() throws Exception {
        int numberOfBirdsBeforeDelete = getAllBirds().length;
        UUID birdId = getTestBird().getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/birds/" + birdId));
        int numberOfBirdsAfterDelete = getAllBirds().length;

        assertEquals(numberOfBirdsBeforeDelete - 1, numberOfBirdsAfterDelete);
    }

    private Bird[] getAllBirds() throws Exception {
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/birds")))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        return mapper.readValue(contentAsJson, Bird[].class);
    }

    private Bird getTestBird() {
        String json = """
                {
                     "id": "952a60c2-e4ad-422c-bb55-7f1aad97c15d",
                     "name": "Henry",
                     "speciesName": "Pigeon",
                     "birthDate": "18-01-2022",
                     "habitat": "Woods",
                     "behaviour": "calm and chirpy",
                     "foodType": "seeds",
                     "extraInformation": "Annoying and noisy!",
                     "canMimicSound": false,
                     "nocturnal": false
                }""";

        try {
            Bird bird = mapper.readValue(json, Bird.class);
            bird.setZoo(getTestZoo());
            return bird;
        } catch (JsonProcessingException e) {
            return new Bird();
        }
    }

    private Zoo getTestZoo() {
        String json = """
                 {
                    "id": "7cf4649d-32a6-40a8-9160-92073bf64b13",
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
