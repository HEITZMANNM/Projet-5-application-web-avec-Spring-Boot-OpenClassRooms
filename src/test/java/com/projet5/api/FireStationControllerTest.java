package com.projet5.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet5.api.controller.FireStationsController;
import com.projet5.api.controller.PersonsController;
import com.projet5.api.model.*;
import com.projet5.api.service.FireStationsService;
import com.projet5.api.service.PersonsService;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FireStationsController.class)
public class FireStationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FireStationsService fireStationsService;


    @Test
    public void testGetPersonsCoveredByFireStationNumberAndNumberOfChildren() throws Exception {
        mockMvc.perform(get("/firestation?stationNumber=3"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPhoneNumberOfPersonsCoveredByFireStationNumber() throws Exception {
        mockMvc.perform(get("/phoneAlert?stationNumber=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFireStationNumberAndPersonsByAddress() throws Exception {
        mockMvc.perform(get("/fire?address=1509 Culver St"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFamiliesCoveredByFireStationNumber() throws Exception {
        mockMvc.perform(get("/flood?stationNumber=1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPostNewFireStation() throws Exception {


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

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
