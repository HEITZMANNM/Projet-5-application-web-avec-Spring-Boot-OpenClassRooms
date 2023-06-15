package com.projet5.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FireStationNumberAndPersonsByAddress that = (FireStationNumberAndPersonsByAddress) o;
        return fireStationNumber == that.fireStationNumber && Objects.equals(listOfPersonWithMedicalRecords, that.listOfPersonWithMedicalRecords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listOfPersonWithMedicalRecords, fireStationNumber);
    }
}
