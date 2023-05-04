package com.projet5.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet5.api.controller.PersonsController;
import com.projet5.api.model.MedicalRecords;
import com.projet5.api.model.Persons;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import com.projet5.api.service.PersonsService;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//test the PersonsController, with its different endpoints
@ExtendWith(MockitoExtension.class)
public class PersonsControllerTest {

    private MockMvc mockMvc;

    private PersonsService personsService;

    @InjectMocks
    private PersonsController personsController;

    private JSONReaderFromURLIMPL jsonReaderFromURLIMPL = new JSONReaderFromURLIMPL()
    {
        @Override
        public List<Persons> getPersons()
        {
            List<Persons>listOfAllPersons = new ArrayList<>();
            MedicalRecords medicalRecords = new MedicalRecords();
            List<String>allergies = new ArrayList<>();
            String allergie = "peanut";
            allergies.add(allergie);
            medicalRecords.setAllergies(allergies);

            Persons beth = new Persons("Beth", "Dutton", "11 yellowstone way", "Montana city", 0, null, "BD@gmail.com", null, medicalRecords, 10);
            listOfAllPersons.add(beth);

            return listOfAllPersons;
        }
    };

    @BeforeEach
    public void setUp() throws JSONException, JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(personsController)
                .build();
        personsService = new PersonsService();
        personsController.setPersonsService(personsService);
        personsService.setJsonReaderFromURLIMPL(jsonReaderFromURLIMPL);
    }

    @Test
    public void schouldCreateMockMvc()
    {
        assertNotNull(mockMvc);
    }

    @Test
    public void testGetChildrenAtTheAddressAndOtherMemberOfFamily() throws Exception {

        String address = "11 yellowstone way";
        this.mockMvc.perform(get("/childAlert").param("address", address))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.listOfChildren.[0].firstName").value("Beth"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.listOfChildren.[0].lastName").value("Dutton"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.listOfChildren.[0].age").value(10));
    }

    @Test
    public void testGetPersonInfo() throws Exception {

        this.mockMvc.perform(get("/personInfo?firstName=Beth&lastName=Dutton")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName").value("Beth"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].email").value("BD@gmail.com"));
    }

    @Test
    public void testGetEmailOfPersonsWhoLiveAtSelectedCity() throws Exception {
        mockMvc.perform(get("/communityEmail?city=Montana city")).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].email").value("BD@gmail.com"));
    }

    @Test
    public void testPostNewPerson() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                        .post("/person")
                        .content(asJsonString(new Persons("John", "Dutton", "11 yellowstone way","Montana city",0, null, "JDutton@gmail.fr", null, null, 40)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }


    @Test
    public void testUpDatePerson() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/person")
                        .content(asJsonString(new Persons("Beth", "Dutton", "55 Ranch street","Montana city",0, null, null, null, null, 40)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpDateAUnknownPerson() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/person")
                        .content(asJsonString(new Persons("Bill", "Banner", "55 Ranch street","Montana city",0, null, null, null, null, 40)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeletePerson() throws Exception {

        mockMvc.perform( MockMvcRequestBuilders
                        .delete("/person?firstName=Beth&lastName=Dutton"))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testDeleteAUnknownPerson() throws Exception {

        mockMvc.perform( MockMvcRequestBuilders
                        .delete("/person?firstName={}&lastName={}", "Bill", "Banner"))
                .andExpect(status().isBadRequest());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}




