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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//test the fireStationController, with its different endpoints
@ExtendWith(MockitoExtension.class)
public class FireStationControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private FireStationsController fireStationsController;

    private FireStationsService fireStationsService;

    private JSONReaderFromURLIMPL jsonReaderFromURLIMPL = new JSONReaderFromURLIMPL() {
        @Override
        public List<FireStations> getFireStations() throws JSONException, JsonProcessingException {
            List<FireStations> listOfFireStationByNumber3 = new ArrayList<>();
            int stationNumber = 3;
            String addressOne = "11 way of Yellowstone";
            String addressTwo = "15 street of Montana";

            FireStations fireStationOne = new FireStations();
            fireStationOne.setStation(stationNumber);
            fireStationOne.setAddress(addressOne);

            FireStations fireStationTwo = new FireStations();
            fireStationTwo.setAddress(addressTwo);
            fireStationTwo.setStation(stationNumber);


            listOfFireStationByNumber3.add(fireStationOne);
            listOfFireStationByNumber3.add(fireStationTwo);

            int stationNumber4 = 4;
            String addressThree = "22 high street";

            FireStations fireStationsThree = new FireStations();
            fireStationsThree.setStation(stationNumber4);
            fireStationsThree.setAddress(addressThree);

            listOfFireStationByNumber3.add(fireStationsThree);
            return listOfFireStationByNumber3;
        }

        @Override
        public List<Persons> getPersons() {
            String addressOne = "11 way of Yellowstone";
            String addressTwo = "15 street of Montana";
            List<Persons> persons = new ArrayList<>();
            MedicalRecords medicalRecordsChild = new MedicalRecords();
            medicalRecordsChild.setFirstName("Beth");
            medicalRecordsChild.setLastName("Dutton");
            List<String>allergies = new ArrayList<>();
            allergies.add("peanut");
            medicalRecordsChild.setAllergies(allergies);

            Persons child = new Persons("Beth", "Dutton", "11 way of Yellowstone", "Montana city", 0, "888-888-888", null, null, medicalRecordsChild, 10);
            Persons father = new Persons("John", "Dutton", "11 way of Yellowstone", "Montana city", 0, "999-999-999", null, null, null, 45);

            persons.add(child);
            persons.add(father);

            Persons mother = new Persons();
            Persons aunt = new Persons();

            mother.setAddress(addressOne);
            mother.setFirstName("Marta");
            mother.setLastName("Dutton");
            mother.setAge(41);
            mother.setCity("MontanaCity");

            aunt.setAddress(addressTwo);
            aunt.setFirstName("Lisa");
            aunt.setLastName("Dutton");
            aunt.setAge(40);
            aunt.setCity("Havre");

            persons.add(aunt);
            persons.add(mother);

            return persons;
        }
    };



    @BeforeEach
    public void setUp() throws JSONException, JsonProcessingException
    {
        mockMvc = MockMvcBuilders.standaloneSetup(fireStationsController)
                .build();
        fireStationsService = new FireStationsService();
       fireStationsController.setFireStationsService(fireStationsService);
       fireStationsService.setJsonReaderFromURLIMPL(jsonReaderFromURLIMPL);
    }


    @Test
    public void testGetPersonsCoveredByFireStationNumberAndNumberOfChildren() throws Exception
    {
        mockMvc.perform(get("/firestation").param("stationNumber", "3"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.listOfPersonsCovered.[0].firstName").value("Beth"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.numberOfChildren").value(1));
    }

    @Test
    public void testGetPhoneNumberOfPersonsCoveredByFireStationNumber() throws Exception
    {
        mockMvc.perform(get("/phoneAlert?firestation=3"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].phone").value("888-888-888"));
    }

    @Test
    public void testGetFireStationNumberAndPersonsByAddress() throws Exception
    {
        mockMvc.perform(get("/fire?address=11 way of Yellowstone"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.listOfPersonWithMedicalRecords.[0].firstName").value("Beth"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.fireStationNumber").value("3"));
    }

    @Test
    public void testGetFamiliesCoveredByFireStationNumber() throws Exception
    {
        mockMvc.perform(get("/flood/stations?stations=3,4"))
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
                        .put("/firestation?address=22 high street&stationNumber=77777")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpDateAUnknownFireStation() throws Exception
    {
        mockMvc.perform( MockMvcRequestBuilders
                        .put("/firestation?address=7777 high street&stationNumber=77777")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testToDeleteFireStationByAddress() throws Exception
    {

        mockMvc.perform( MockMvcRequestBuilders
                        .delete("/firestationByAddress?address={address}", "11 way of Yellowstone"))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testToDeleteFireStationByUnknownAddress() throws Exception
    {

        mockMvc.perform( MockMvcRequestBuilders
                        .delete("/firestationByAddress?address={address}", "5555 way of Yellowstone"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testToDeleteFireStationByStationNumber() throws Exception
    {

        mockMvc.perform( MockMvcRequestBuilders
                        .delete("/firestationByStationNumber?stationNumber=4"))
                .andExpect(status().isAccepted());
    }

    @Test
    public void testToDeleteFireStationByUnknownStationNumber() throws Exception
    {

        mockMvc.perform( MockMvcRequestBuilders
                        .delete("/firestationByStationNumber?stationNumber=7777"))
                .andExpect(status().isBadRequest());
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
