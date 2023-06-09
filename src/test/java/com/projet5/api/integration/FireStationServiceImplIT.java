package com.projet5.api.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.projet5.api.model.FireStations;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import com.projet5.api.service.FireStationsService;
import com.projet5.api.service.FireStationsServiceImpl;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class FireStationServiceImplIT {

    private static JSONReaderFromURLIMPL jsonReaderFromURLIMPL;

    private static FireStationsService fireStationsService;

    //crete a fireStation to test the different methods of FireStationService
    @BeforeEach
    public void setUp() throws JSONException, JsonProcessingException
    {
        jsonReaderFromURLIMPL = new JSONReaderFromURLIMPL();
        fireStationsService = new FireStationsServiceImpl();

        fireStationsService.setJsonReaderFromURLIMPL(jsonReaderFromURLIMPL);

        FireStations fireStationToSave = new FireStations();
        fireStationToSave.setStation(2222);
        fireStationToSave.setAddress("22 Tortuga street");

        fireStationsService.addANewFireStation(fireStationToSave);
    }

    //test to save a new FireStation
    @Test
    public void testToSaveANewFireStation() throws JSONException, JsonProcessingException
    {
        FireStations fireStationSearch = jsonReaderFromURLIMPL.getFireStationByAddress("22 Tortuga street");

        assertEquals(fireStationSearch.getStation(), 2222);
    }

    //test to delete a FireStation by its address
    @Test
    public void testToDeleteAFireStationByAddress() throws JSONException, JsonProcessingException
    {
        fireStationsService.deleteFireStationByAddress("22 Tortuga street");

        FireStations fireStationSearch = fireStationsService.getJsonReaderFromURLIMPL().getFireStationByAddress("22 Tortuga street");

        assertEquals(fireStationSearch.getStation(), 0);
        assertNull(fireStationSearch.getAddress());
    }

    //test to delete a fire station by its stationNumber
    @Test
    public void testToDeleteAFireStationByItsAddress() throws JSONException, JsonProcessingException
    {
        fireStationsService.deleteFireStationByStationNumber(2222);

        FireStations fireStationSearch = fireStationsService.getJsonReaderFromURLIMPL().getFireStationByAddress("22 Tortuga street");

        assertEquals(fireStationSearch.getStation(), 0);
        assertNull(fireStationSearch.getAddress());
    }

    //test to upDate FireStation's number
    @Test
    public void testToUpDateFireStationNumber() throws JSONException, JsonProcessingException
    {
        String addressOfFireStationToUpDate = "22 Tortuga street";
        int newStationNumber = 3333;

        fireStationsService.upDateStationNumber(addressOfFireStationToUpDate, newStationNumber);

        FireStations fireStationUpDated = fireStationsService.getJsonReaderFromURLIMPL().getFireStationByAddress(addressOfFireStationToUpDate);

        assertEquals(fireStationUpDated.getStation(), newStationNumber);
    }
}
