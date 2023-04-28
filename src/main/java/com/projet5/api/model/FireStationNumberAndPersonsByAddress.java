package com.projet5.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.List;

@Data
public class FireStationNumberAndPersonsByAddress {

    @JsonView(View.ListOfPersonWithMedicalRecords.class)
    private List<Persons> listOfPersonWithMedicalRecords;

    @JsonView(View.StationNumber.class)
    private int fireStationNumber;

    public FireStationNumberAndPersonsByAddress(List<Persons> listOfPersonWithMedicalRecords, int fireStationNumber)
    {
        this.fireStationNumber = fireStationNumber;
        this.listOfPersonWithMedicalRecords = listOfPersonWithMedicalRecords;
    }
    public FireStationNumberAndPersonsByAddress()
    {
    }
}
