package com.projet5.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet5.api.controller.FireStationsController;
import com.projet5.api.model.FireStations;
import com.projet5.api.model.MedicalRecords;
import com.projet5.api.model.Persons;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import com.projet5.api.service.FireStationsService;
import org.json.JSONException;
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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//test the fireStationController, with its different endpoints
@ExtendWith(MockitoExtension.class)
public class FireStationControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private FireStationsController fireStationsController;

    private FireStationsService fireStationsService;

    @Mock(lenient = true)
    private JSONReaderFromURLIMPL jsonReaderFromURLIMPL;

    private List<FireStations> listOfAllFireStations;

    @BeforeEach
    public void setUp() throws JSONException, JsonProcessingException
    {
        mockMvc = MockMvcBuilders.standaloneSetup(fireStationsController)
                .build();
        fireStationsService = new FireStationsService();
        fireStationsController.setFireStationsService(fireStationsService);
        fireStationsService.setJsonReaderFromURLIMPL(jsonReaderFromURLIMPL);

        listOfAllFireStations = new ArrayList<>();

        List<Persons> listOfAllPersons = new ArrayList<>();

        FireStations fireStationOne = new FireStations();
        fireStationOne.setStation(1);
        fireStationOne.setAddress("11 yellowstone street");


        listOfAllFireStations.add(fireStationOne);

        MedicalRecords medicalRecordsChild = new MedicalRecords();
        medicalRecordsChild.setFirstName("Beth");
        medicalRecordsChild.setLastName("Dutton");
        List<String>allergies = new ArrayList<>();
        allergies.add("peanut");
        medicalRecordsChild.setAllergies(allergies);

        Persons child = new Persons("Beth", "Dutton", "11 yellowstone street", "Montana city", 0, "888-888-888", null, null, medicalRecordsChild, 10);
        Persons father = new Persons("John", "Dutton", "11 yellowstone street", "Montana city", 0, "999-999-999", null, null, null, 45);

        listOfAllPersons.add(child);
        listOfAllPersons.add(father);

        when(jsonReaderFromURLIMPL.getFireStationByStationNumber(anyInt())).thenReturn(listOfAllFireStations);
        when(jsonReaderFromURLIMPL.getAllPersonsByAddress(anyString())).thenReturn(listOfAllPersons);
        when(jsonReaderFromURLIMPL.getFireStationByAddress(anyString())).thenReturn(fireStationOne);
        when(jsonReaderFromURLIMPL.getFireStations()).thenReturn(listOfAllFireStations);
    }


    @Test
    public void testGetPersonsCoveredByFireStationNumberAndNumberOfChildren() throws Exception
    {
        mockMvc.perform(get("/firestation").param("stationNumber", "1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.listOfPersonsCovered.[0].firstName").value("Beth"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfChildren").value(1));
    }

    @Test
    public void testGetPhoneNumberOfPersonsCoveredByFireStationNumber() throws Exception
    {
        mockMvc.perform(get("/phoneAlert?stationNumber=1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].phone").value("888-888-888"));
    }

    @Test
    public void testGetFireStationNumberAndPersonsByAddress() throws Exception
    {
        mockMvc.perform(get("/fire?address=11 yellowstone street"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.listOfPersonWithMedicalRecords.[0].firstName").value("Beth"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fireStationNumber").value("1"));
    }

    @Test
    public void testGetFamiliesCoveredByFireStationNumber() throws Exception
    {
        mockMvc.perform(get("/flood/stations?stationNumber=1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].[0].medicalRecords.allergies.[0]").value("peanut"));
    }

    @Test
    public void testPostNewFireStation() throws Exception
    {
        FireStations fireStations = new FireStations();
        fireStations.setStation(5555);
        fireStations.setAddress("11 yellowstone way");

        mockMvc.perform( MockMvcRequestBuilders
                        .post("/firestation")
                        .content(asJsonString(fireStations))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpDateFireStation() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/firestation?address=11 yellowstone street&stationNumber=77777")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testToDeleteFireStationByAddress() throws Exception
    {
        FireStations fireStation = new FireStations();
        fireStation.setAddress("55 yellowstone street");
        fireStation.setStation(5555);

        fireStationsService.addANewFireStation(fireStation);

        mockMvc.perform( MockMvcRequestBuilders
                        .delete("/firestationByAddress?address={address}", "55 yellowstone street"))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testToDeleteFireStationByStationNumber() throws Exception
    {
        FireStations fireStation = new FireStations();
        fireStation.setAddress("55 yellowstone street");
        fireStation.setStation(7777);

        fireStationsService.addANewFireStation(fireStation);

        mockMvc.perform( MockMvcRequestBuilders
                        .delete("/firestationByStationNumber?stationNumber={stationNumber}", 7777))
                .andExpect(status().isAccepted());
    }

    public static String asJsonString(final Object obj)
    {
        try
        {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
