package com.projet5.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.projet5.api.model.*;
import com.projet5.api.service.FireStationsService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class FireStationsController {

    @Autowired
    private FireStationsService fireStationsService;

@JsonView(View.PeopleCoveredByFireStationAndNumberOfChildren.class)
    @GetMapping("/firestation")
    public PeopleCoveredByFireStationAndNumberOfChildren getPersonsCoveredByFireStationNumberAndNumberOfChildren(@RequestParam(name="stationNumber") int stationNumber) throws JSONException, IOException {

        return fireStationsService.getPersonsCoveredByFireStationNumberAndNumberOfChildren(stationNumber);
    }

    @JsonView(View.Phone.class)
    @GetMapping("/phoneAlert")
    public List<Persons> getPhoneNumberOfPersonsCoveredByFireStationNumber(@RequestParam(name = "stationNumber") int stationNumber) throws JSONException, IOException {
        return fireStationsService.getPersonsCoveredByFireStationNumber(stationNumber);
    }

    @JsonView(View.ListOfPersonWithMedicalRecords.class)
    @GetMapping("/fire")
    public FireStationNumberAndPersonsByAddress getFireStationNumberAndPersonsByAddress(@RequestParam(name = "address") String address)
    {
        return fireStationsService.getPersonsWhoLiveAtTheAddressAndFireStationWhichCoverThem(address);
    }

    @JsonView(View.FamilysPersonsCoveredByStationNumber.class)
    @GetMapping("/flood")
    public List<List<Persons>> getFamiliesCoveredByFireStationNumber(@RequestParam(name = "stationNumber") int stationNumber){
    return fireStationsService.getFamiliesCoveredByFireStationNumber(stationNumber);
    }

    @PostMapping("/firestation")
    public void saveNewFireStation (@RequestBody FireStations fireStation) throws JSONException, JsonProcessingException {
        fireStationsService.addANewFireStation(fireStation);
    }

    @DeleteMapping("/firestation")
    public void deleteFireStation(@RequestParam(required = false )int stationNumber, @RequestParam(required = false) String address) throws JSONException, JsonProcessingException {
        fireStationsService.deleteFireStation(address, stationNumber);
    }

    @PutMapping("/firestation")
    public void upDateStationNumber(@RequestParam(name = "address") String address, @RequestParam(name = "stationNumber") int stationNumberToChange) throws JSONException, JsonProcessingException {
        fireStationsService.upDateStationNumber(address, stationNumberToChange);
    }

@GetMapping("/firestationAll")
    public List<FireStations> getAllFirestations() throws JSONException, JsonProcessingException {
return fireStationsService.getAllFireStation();

}
}
