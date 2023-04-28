package com.projet5.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.projet5.api.model.*;
import com.projet5.api.service.FireStationsService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.List;


@RestController
public class FireStationsController {

    @Autowired
    private FireStationsService fireStationsService;

@JsonView(View.PersonsFirstNameLastNameAddressAndPhoneOnly.class)
    @GetMapping(value = "/firestation", produces = "application/json")
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

    @JsonView(View.FamiliesPersonsCoveredByStationNumber.class)
    @GetMapping("/flood/stations")
    public List<List<Persons>> getFamiliesCoveredByFireStationNumber(@RequestParam(name = "stationNumber") int stationNumber){
    return fireStationsService.getFamiliesCoveredByFireStationNumber(stationNumber);
    }

    @PostMapping("/firestation")
    public ResponseEntity<Void> saveNewFireStation (@RequestBody FireStations fireStation) throws JSONException, JsonProcessingException {
        fireStationsService.addANewFireStation(fireStation);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/fire?address={}")
                .buildAndExpand(fireStation.getAddress())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    @DeleteMapping("/firestationByStationNumber")
    public ResponseEntity<HttpStatus> deleteFireStationByStationNumber(@RequestParam int stationNumber) throws JSONException, JsonProcessingException {
        fireStationsService.deleteFireStationByStationNumber(stationNumber);

        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/firestationByAddress")
    public ResponseEntity<HttpStatus> deleteFireStationByAddress(@RequestParam String address) throws JSONException, JsonProcessingException {
        fireStationsService.deleteFireStationByAddress(address);

        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    @PutMapping("/firestation")
    public void upDateStationNumber(@RequestParam(name = "address") String address, @RequestParam(name = "stationNumber") int stationNumberToChange) throws JSONException, JsonProcessingException {
        fireStationsService.upDateStationNumber(address, stationNumberToChange);
    }






    //pour tester
//    @GetMapping("/firestationAll")
//    public List<FireStations> getAllFireStations() throws JSONException, JsonProcessingException {
//        return fireStationsService.getAllFireStation();
//    }

}
