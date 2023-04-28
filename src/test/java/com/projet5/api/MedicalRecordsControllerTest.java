package com.projet5.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet5.api.controller.MedicalRecordsController;
import com.projet5.api.model.MedicalRecords;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import com.projet5.api.service.MedicalRecordsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordsControllerTest
{
    private MockMvc mockMvc;

    private MedicalRecordsService medicalRecordsService;

    @InjectMocks
    private MedicalRecordsController medicalRecordsController;

    @Mock(lenient = true)
    private JSONReaderFromURLIMPL jsonReaderFromURLIMPL;

    @BeforeEach
    public void setUp()
    {
        mockMvc = MockMvcBuilders.standaloneSetup(medicalRecordsController)
                .build();
        medicalRecordsService = new MedicalRecordsService();
        medicalRecordsController.setMedicalRecordsService(medicalRecordsService);
        medicalRecordsService.setJsonReaderFromURLIMPL(jsonReaderFromURLIMPL);

        List<MedicalRecords> listOfAllMedicalRecords = new ArrayList<>();
        MedicalRecords medicalRecords = new MedicalRecords();
        medicalRecords.setLastName("Dutton");
        medicalRecords.setFirstName("Beth");
        List<String> allergies = new ArrayList<>();
        allergies.add("peanut");
        medicalRecords.setAllergies(allergies);
        listOfAllMedicalRecords.add(medicalRecords);

        when(jsonReaderFromURLIMPL.getMedicalRecords()).thenReturn(listOfAllMedicalRecords);

    }

    @Test
    public void schouldCreateMockMvc()
    {
        assertNotNull(mockMvc);
    }

    @Test
    public void testGetAllMedicalRecords() throws Exception {

        mockMvc.perform(get("/medicalRecord"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].firstName").value("Beth"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].lastName").value("Dutton"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].allergies.[0]").value("peanut"));
    }

    @Test
    public void testToPostNewMedicalRecords() throws Exception {

        MedicalRecords medicalRecordsToSave = new MedicalRecords();
        medicalRecordsToSave.setFirstName("John");
        medicalRecordsToSave.setLastName("Dutton");

        this.mockMvc.perform( MockMvcRequestBuilders
                        .post("/medicalRecord")
                        .content(asJsonString((medicalRecordsToSave)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpDateMedialRecords() throws Exception {

        MedicalRecords medicalRecordsToAdd = new MedicalRecords();
        medicalRecordsToAdd.setLastName("Dutton");
        medicalRecordsToAdd.setFirstName("Beth");
        List<String> allergies = new ArrayList<>();
        allergies.add("peanut");
        medicalRecordsToAdd.setAllergies(allergies);

        medicalRecordsService.saveNewMedicalRecords(medicalRecordsToAdd);

        MedicalRecords medicalRecordsToUpDate = new MedicalRecords();
        medicalRecordsToUpDate.setLastName("Dutton");
        medicalRecordsToUpDate.setFirstName("Beth");
        List<String> allergiesUpDated = new ArrayList<>();
        allergies.add("Lemon");
        medicalRecordsToUpDate.setAllergies(allergies);

        mockMvc.perform( MockMvcRequestBuilders
                        .put("/medicalRecord")
                        .content(asJsonString(medicalRecordsToUpDate))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.allergies.[1]").value("Lemon"));
    }

    @Test
    public void testToDeleteMedicalRecords() throws Exception {
        MedicalRecords medicalRecordsToAdd = new MedicalRecords();
        medicalRecordsToAdd.setLastName("Dutton");
        medicalRecordsToAdd.setFirstName("Beth");
        List<String> allergies = new ArrayList<>();
        allergies.add("peanut");
        medicalRecordsToAdd.setAllergies(allergies);

        medicalRecordsService.saveNewMedicalRecords(medicalRecordsToAdd);

        mockMvc.perform( MockMvcRequestBuilders
                        .delete("/medicalRecord?firstName={}&lastName={}", "Beth", "Dutton"))
                .andExpect(status().isAccepted());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
