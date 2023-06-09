package com.projet5.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet5.api.controller.MedicalRecordsController;
import com.projet5.api.model.MedicalRecords;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import com.projet5.api.service.MedicalRecordsServiceImpl;
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


//test the MedicalRecordsController, with its different endpoints
@ExtendWith(MockitoExtension.class)
public class MedicalRecordsControllerTest
{
    private MockMvc mockMvc;

    private MedicalRecordsServiceImpl medicalRecordsService;

    @InjectMocks
    private MedicalRecordsController medicalRecordsController;

    private JSONReaderFromURLIMPL jsonReaderFromURLIMPL = new JSONReaderFromURLIMPL()
    {
        @Override
        public List<MedicalRecords> getMedicalRecords()
        {
            List<MedicalRecords> listOfAllMedicalRecords = new ArrayList<>();
            MedicalRecords medicalRecords = new MedicalRecords();
            medicalRecords.setLastName("Dutton");
            medicalRecords.setFirstName("Beth");
            List<String> allergies = new ArrayList<>();
            allergies.add("peanut");
            List<String> medications = new ArrayList<>();
            medicalRecords.setAllergies(allergies);
            medicalRecords.setMedications(medications);
            listOfAllMedicalRecords.add(medicalRecords);

            return listOfAllMedicalRecords;
        }
    };

    @BeforeEach
    public void setUp()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(medicalRecordsController)
                .build();
        medicalRecordsService = new MedicalRecordsServiceImpl();
        medicalRecordsController.setMedicalRecordsService(medicalRecordsService);
        medicalRecordsService.setJsonReaderFromURLIMPL(jsonReaderFromURLIMPL);
    }

    @Test
    public void schouldCreateMockMvc()
    {
        assertNotNull(mockMvc);
    }

    //test to get all medicalRecords when we used the endpoint
    @Test
    public void testGetAllMedicalRecords() throws Exception {

        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName").value("Beth"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].lastName").value("Dutton"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].allergies.[0]").value("peanut"));
    }
    //test to post a new medicalRecords using the endpoint
    @Test
    public void testToPostNewMedicalRecords() throws Exception {

        MedicalRecords medicalRecordsToSave = new MedicalRecords();
        medicalRecordsToSave.setFirstName("Beth");
        medicalRecordsToSave.setLastName("Dutton");
        List<String>medicationsToSave = new ArrayList<>();
        medicationsToSave.add("doliprane : 100mg");
        medicalRecordsToSave.setMedications(medicationsToSave);

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/medicalRecord?firstName=Beth&lastName=Dutton")
                        .content(asJsonString((medicalRecordsToSave)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    //test to upDate a medicalRecords using the endpoint
    @Test
    public void testUpDateMedialRecords() throws Exception {

        MedicalRecords medicalRecordsToUpDate = new MedicalRecords();
        medicalRecordsToUpDate.setLastName("Dutton");
        medicalRecordsToUpDate.setFirstName("Beth");
        List<String> allergiesUpDated = new ArrayList<>();
        allergiesUpDated.add("Lemon");
        medicalRecordsToUpDate.setAllergies(allergiesUpDated);

        mockMvc.perform( MockMvcRequestBuilders
                        .put("/medicalRecord")
                        .content(asJsonString(medicalRecordsToUpDate))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpDateUnknownMedialRecords() throws Exception {

        MedicalRecords medicalRecordsToUpDate = new MedicalRecords();
        medicalRecordsToUpDate.setLastName("Banner");
        medicalRecordsToUpDate.setFirstName("Bill");
        List<String> allergiesUpDated = new ArrayList<>();
        allergiesUpDated.add("Lemon");
        medicalRecordsToUpDate.setAllergies(allergiesUpDated);

        mockMvc.perform( MockMvcRequestBuilders
                        .put("/medicalRecord")
                        .content(asJsonString(medicalRecordsToUpDate))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    //test to delete a medicalRecords using the endpoint
    @Test
    public void testToDeleteMedicalRecords() throws Exception {

        mockMvc.perform( MockMvcRequestBuilders
                        .delete("/medicalRecord?firstName=Beth&lastName=Dutton"))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testToDeleteUnknownMedicalRecords() throws Exception {

        mockMvc.perform( MockMvcRequestBuilders
                        .delete("/medicalRecord?firstName=Bill&lastName=Banner"))
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
