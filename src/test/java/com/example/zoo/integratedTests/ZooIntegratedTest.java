package com.example.zoo.integratedTests;

import com.example.zoo.entities.Zoo;
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
class ZooIntegratedTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Test
    void test_GetAllZoos_ValidRequest() throws Exception {
        Zoo[] actualZoos = getAllZoos();

        assertEquals(4, actualZoos.length);
    }

    private Zoo[] getAllZoos() throws Exception {
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/zoos")))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        return mapper.readValue(contentAsJson, Zoo[].class);
    }

    @Test
    void test_GetZooById_ValidRequest() throws Exception {
        UUID zooId = UUID.fromString("40ea5519-fcef-4272-b742-e01790ca04c3");
        MvcResult result =
                (this.mockMvc.perform(MockMvcRequestBuilders.get("/zoos/" + zooId)))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        String contentAsJson = result.getResponse().getContentAsString();
        Zoo zoo = mapper.readValue(contentAsJson, Zoo.class);

        assertAll(
                () -> assertEquals(zooId, zoo.getId()),
                () -> assertEquals("Edinburgh Zoo", zoo.getName())
        );
    }

    @Test
    void test_AddZoo_ValidRequest() throws Exception {
        int numberOfZoosBeforeAdd = getAllZoos().length;
        Zoo zoo = new Zoo();
        zoo.setName("Test");
        String json = mapper.writeValueAsString(zoo);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/zoos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = (mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn());

        String contentAsJson = result.getResponse().getContentAsString();
        Zoo zooResult = mapper.readValue(contentAsJson, Zoo.class);

        int numberOfZoosAfterAdd = getAllZoos().length;

        assertAll(
                () -> assertEquals(zoo.getName(), zooResult.getName()),
                () -> assertEquals(numberOfZoosBeforeAdd + 1, numberOfZoosAfterAdd)
        );
    }

    @Test
    void test_AddAListOfZoos_ValidRequest() throws Exception {
        int numberOfZoosBeforeAdd = getAllZoos().length;
        List<Zoo> zoos = new ArrayList<>();
        Zoo zoo1 = createAZoo();
        zoos.add(zoo1);
        Zoo zoo2 = new Zoo();
        Zoo zoo3 = createAZoo();
        zoos.add(zoo2);
        zoos.add(zoo3);
        String json = mapper.writeValueAsString(zoos);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/zoos/addZoos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder);
        int numberOfZoosAfterAdd = getAllZoos().length;

        assertAll(

                () -> assertEquals(numberOfZoosBeforeAdd + 1, numberOfZoosAfterAdd)
        );

    }

    @Test
    void test_UpdateZoo_ValidRequest() throws Exception {
        int numberOfZoosBeforeAdd = getAllZoos().length;
        Zoo zoo = createAZoo();
        String json = mapper.writeValueAsString(zoo);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/zoos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        MvcResult result = (mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn());

        String contentAsJson = result.getResponse().getContentAsString();
        Zoo zoo1 = mapper.readValue(contentAsJson, Zoo.class);

        int numberOfZoosAfterAdd = getAllZoos().length;

        assertAll(
                () -> assertEquals(zoo.getName(), zoo1.getName()),
                () -> assertEquals(numberOfZoosBeforeAdd, numberOfZoosAfterAdd)
        );
    }

    @Test
    void test_DeleteZooById_ValidRequest() throws Exception {
        int numberOfZooBeforeDelete = getAllZoos().length;
        UUID zooId = UUID.fromString("3931d736-38bc-4cf6-ae01-116d5969e69e");

        mockMvc.perform(MockMvcRequestBuilders.delete("/zoos/" + zooId));
        int numberOfZoosAfterDelete = getAllZoos().length;

        assertEquals(numberOfZooBeforeDelete - 1, numberOfZoosAfterDelete);
    }

    private Zoo createAZoo() {
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String json = "{\n" +
                "    \"id\": \"40ea5519-fcef-4272-b742-e01790ca04c3\",\n" +
                "    \"name\": \"Chester Zoo\",\n" +
                "    \"location\": \"Upton-by-Chester, Cheshire, England\",\n" +
                "    \"capacity\": 27000,\n" +
                "    \"price\": 19,\n" +
                "    \"dateOpened\": \"1931-06-10\"\n" +
                "  }";
        try{
            return mapper.readValue(json, Zoo.class);
        } catch (Exception e) {
            return new Zoo();
        }


    }
}
