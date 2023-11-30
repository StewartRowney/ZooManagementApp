package com.example.zoo.integratedTests;

import com.example.zoo.entities.Insect;
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
class InsectIntegratedTest {

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

    @Test
    void test_AddInsect_ValidRequest() throws Exception {
        int numberOfInsectsBeforeAdd = getAllInsects().length;
        Insect insect = new Insect();
        insect.setName("Test");
        insect.setSpeciesName("testAnimal");
        insect.setZoo(getTestZoo());
        String json = mapper.writeValueAsString(insect);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/insects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = (mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn());

        String contentAsJson = result.getResponse().getContentAsString();
        Insect insectResult = mapper.readValue(contentAsJson, Insect.class);

        int numberOfInsectsAfterAdd = getAllInsects().length;

        assertAll(
                () -> assertEquals(insect.getName(), insectResult.getName()),
                () -> assertEquals(insect.getSpeciesName(), insectResult.getSpeciesName()),
                () -> assertEquals(numberOfInsectsBeforeAdd + 1, numberOfInsectsAfterAdd)
        );
    }

    @Test
    void test_UpdateInsect_ValidRequest() throws Exception {
        int numberOfInsectsBeforeUpdate = getAllInsects().length;
        Insect insect = getTestInsect();
        String json = mapper.writeValueAsString(insect);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/insects")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = (mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn());

        String contentAsJson = result.getResponse().getContentAsString();
        Insect insectResult = mapper.readValue(contentAsJson, Insect.class);

        int numberOfInsectsAfterUpdate = getAllInsects().length;

        assertAll(
                () -> assertEquals(insect.getName(), insectResult.getName()),
                () -> assertEquals(insect.getSpeciesName(), insectResult.getSpeciesName()),
                () -> assertEquals(numberOfInsectsBeforeUpdate, numberOfInsectsAfterUpdate)
        );
    }

    @Test
    void test_DeleteInsectById_ValidRequest() throws Exception {
        int numberOfInsectsBeforeDelete = getAllInsects().length;
        UUID insectId = getTestInsect().getId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/insects/" + insectId));
        int numberOfInsectsAfterDelete = getAllInsects().length;

        assertEquals(numberOfInsectsBeforeDelete - 1, numberOfInsectsAfterDelete);
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

    private Insect getTestInsect() {
        String json = """
                {
                    "id": "8a3cdf04-19b5-4acc-90d7-8ad8abef523e",
                    "name": "Sid",
                    "speciesName": "Spider",
                    "birthDate": "10-10-2020",
                    "habitat": "Trees",
                    "behaviour": "Crazy",
                    "foodType": "Flies",
                    "extraInformation": "is a great guy",
                    "hasWings": false,
                    "numberOfLegs": 8
                  }""";

        try {
            Insect insect = mapper.readValue(json, Insect.class);
            insect.setZoo(getTestZoo());
            return insect;
        } catch (JsonProcessingException e) {
            return new Insect();
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
