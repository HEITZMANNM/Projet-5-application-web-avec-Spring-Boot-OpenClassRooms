package com.projet5.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet5.api.controller.FireStationsController;
import com.projet5.api.controller.MedicalRecordsController;
import com.projet5.api.model.MedicalRecords;
import com.projet5.api.model.Persons;
import com.projet5.api.service.FireStationsService;
import com.projet5.api.service.MedicalRecordsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicalRecordsController.class)
public class MedicalRecordsControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalRecordsService medicalRecordsService;


    @Test
    public void testGetAllMedicalRecords() throws Exception {
        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isOk());
    }

    @Test
    public void testToPostNewMedicalRecords() throws Exception {
        MedicalRecords medicalRecordsToAdd = new MedicalRecords();
        medicalRecordsToAdd.setLastName("Dutton");
        medicalRecordsToAdd.setFirstName("Beth");
        List<String> allergies = new ArrayList<>();
        allergies.add("peanut");
        medicalRecordsToAdd.setAllergies(allergies);

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/medicalRecord")
                        .content(asJsonString(medicalRecordsToAdd))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
