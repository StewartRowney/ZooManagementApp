package stepDefinitions;

import com.example.zoo.entities.Animal;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AnimalsStepdefinitions {

    private Animal[] animals;
    private int numberOfAnimals;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @Given("I have {int} amphibians")
    public void iHaveAmphibians(int amount) {
        numberOfAnimals = amount;
    }

    @When("I request all {word}")
    public void iRequestAllAmphibians(String animalType) throws Exception {
        animals = performMockGetAndReturnAnimals("/" + animalType);
    }

    @Then("The total number of amphibians is correct")
    public void theTotalNumberOfAmphibiansIs() {
        assertEquals(numberOfAnimals, animals.length);
    }

    private Animal[] performMockGetAndReturnAnimals(String url) throws Exception {
        MvcResult result = this.mockMvc.perform(
                        MockMvcRequestBuilders.get(url)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        return mapper.readValue(contentAsString, Animal[].class);
    }
}
