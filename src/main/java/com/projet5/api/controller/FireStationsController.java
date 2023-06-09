package com.projet5.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.projet5.api.model.*;
import com.projet5.api.service.FireStationsServiceImpl;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
public class FireStationsController {

    @Autowired
    private FireStationsServiceImpl fireStationsService;

    @JsonView(View.PersonsFirstNameLastNameAddressAndPhoneOnly.class)
//    @GetMapping(value = "/firestation", produces = "application/json")
    @GetMapping("/firestation")
    public PeopleCoveredByFireStationAndNumberOfChildren getPersonsCoveredByFireStationNumberAndNumberOfChildren(@RequestParam(name="stationNumber") int stationNumber) throws JSONException, IOException {

        PeopleCoveredByFireStationAndNumberOfChildren peopleCoveredByFireStationAndNumberOfChildren = fireStationsService.getPersonsCoveredByFireStationNumberAndNumberOfChildren(stationNumber);


        if(peopleCoveredByFireStationAndNumberOfChildren.getListOfPersonsCovered().isEmpty())
        {
            return null;
        }
        return peopleCoveredByFireStationAndNumberOfChildren;
    }

    @JsonView(View.Phone.class)
    @GetMapping("/phoneAlert")
    public List<Persons> getPhoneNumberOfPersonsCoveredByFireStationNumber(@RequestParam(name = "firestation") int firestation) throws JSONException, IOException {
        return fireStationsService.getPersonsCoveredByFireStationNumber(firestation);
    }

    @JsonView(View.ListOfPersonWithMedicalRecords.class)
    @GetMapping("/fire")
    public FireStationNumberAndPersonsByAddress getFireStationNumberAndPersonsByAddress(@RequestParam(name = "address") String address)
    {
        FireStationNumberAndPersonsByAddress fireStationNumberAndPersonsByAddress = fireStationsService.getPersonsWhoLiveAtTheAddressAndFireStationWhichCoverThem(address);
        if(fireStationNumberAndPersonsByAddress.getListOfPersonWithMedicalRecords().isEmpty())
        {
            return null;
        }
        return fireStationNumberAndPersonsByAddress;
    }

    @JsonView(View.FamiliesPersonsCoveredByStationNumber.class)
    @GetMapping("/flood/stations")
    public List<List<Persons>> getFamiliesCoveredByFireStationNumber(@RequestParam(name = "stations") List<Integer> stations){
        return fireStationsService.getFamiliesCoveredByFireStationNumber(stations);
    }

    @PostMapping("/firestation")
    public ResponseEntity<HttpStatus> saveNewFireStation (@RequestBody FireStations fireStation) throws JSONException, JsonProcessingException {
        if(fireStationsService.addANewFireStation(fireStation))
        {
            return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
        }

        return  new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping("/firestationByStationNumber")
    public ResponseEntity<HttpStatus> deleteFireStationByStationNumber(@RequestParam int stationNumber) throws JSONException, JsonProcessingException {
        if(fireStationsService.deleteFireStationByStationNumber(stationNumber))
        {
            return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/firestationByAddress")
    public ResponseEntity<HttpStatus> deleteFireStationByAddress(@RequestParam String address) throws JSONException, JsonProcessingException {
        if(fireStationsService.deleteFireStationByAddress(address))
        {
            return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/firestation")
    public ResponseEntity<HttpStatus> upDateStationNumber(@RequestParam(name = "address") String address, @RequestParam(name = "stationNumber") int stationNumberToChange) throws JSONException, JsonProcessingException {
        if(fireStationsService.upDateStationNumber(address, stationNumberToChange))
        {
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }

        return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
    }

    public void setFireStationsService(FireStationsServiceImpl fireStationService)
    {
        this.fireStationsService = fireStationService;
    }






    //Used for control on PostMan
    @GetMapping("/firestationAll")
    public List<FireStations> getAllFireStations() throws JSONException, JsonProcessingException {
        return fireStationsService.getAllFireStation();
    }

}
