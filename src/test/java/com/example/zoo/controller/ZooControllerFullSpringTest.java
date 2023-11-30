package com.example.zoo.controller;

import com.example.zoo.entities.Zoo;
import com.example.zoo.services.IZooService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@WebMvcTest(ZooController.class)
@ActiveProfiles("test")
class ZooControllerFullSpringTest {

    @MockBean
    IZooService mockZooService;

    @Autowired
    MockMvc mockMvc;

    private final Zoo zoo = new Zoo();
    private final UUID zooId= UUID.randomUUID();
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());


    @Test
    void test_FindAllZoos_ValidRequest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/zoos");
        mockMvc.perform(requestBuilder);
        verify(mockZooService,times(1)).findAllZoos();
    }

    @Test
    void test_FindZooByID_ValidRequest() throws Exception {
        UUID id = UUID.randomUUID();
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/zoos/findById/" + id);
        mockMvc.perform(requestBuilder);
        verify(mockZooService,times(1)).findZooById(id);
    }

    @Test
    void test_FindZooByName_ValidRequest() throws Exception {
        String name = "zooName";
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/zoos/findByName/" + name);
        mockMvc.perform(requestBuilder);
        verify(mockZooService,times(1)).findZooByName(name);
    }

    @Test
    void test_AddingANewZoo_ValidRequest() throws Exception {
        String json = mapper.writeValueAsString(zoo);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/zoos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockZooService, times(1)).addNewZoo(any(Zoo.class));
    }

    @Test
    void test_AddingAListOfZoos_ValidRequest() throws Exception {
        List<Zoo> zoos = new ArrayList<>();
        zoos.add(zoo);
        String json = mapper.writeValueAsString(zoos);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/zoos/addZoos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockZooService, times(1)).addListOfZoos(Collections.singletonList(any()));
    }

    @Test
    void test_UpdateAZoo_ValidRequest() throws Exception {
        String json = mapper.writeValueAsString(zoo);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/zoos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(mockZooService, times(1)).updateZooWithPut(any(Zoo.class));
    }

    @Test
    void test_PatchAZooName_ValidRequest() throws Exception {
        String name = "name";

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(name);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.patch("/zoos/editName/" + zooId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder);

        verify(mockZooService, times(1)).updateZooByName(any(String.class), any(UUID.class));
    }

    @Test
    void test_RemoveAZoo() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/zoos/deleteZoo/"+zooId);
        mockMvc.perform(requestBuilder);
        verify(mockZooService,times(1)).removeZooById(zooId);
    }

}
