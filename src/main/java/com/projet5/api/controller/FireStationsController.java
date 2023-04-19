package com.projet5.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.projet5.api.model.FireStationNumberAndPersonsByAddress;
import com.projet5.api.model.PeopleCoveredByFireStationAndNumberOfChildren;
import com.projet5.api.model.Persons;
import com.projet5.api.model.View;
import com.projet5.api.service.FireStationsService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

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


}
