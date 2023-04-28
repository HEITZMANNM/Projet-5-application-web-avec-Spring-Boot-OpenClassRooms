package com.projet5.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.List;

@Data
@JsonView(View.PersonsFirstNameLastNameAddressAndPhoneOnly.class)
public class PeopleCoveredByFireStationAndNumberOfChildren {

    List<Persons> listOfPersonsCovered;

    int numberOfChildren;

    int numberOfAdults;

    public PeopleCoveredByFireStationAndNumberOfChildren(List<Persons> listOfPersonsCovered, int numberOfChildren, int numberOfAdults)
    {
        this.listOfPersonsCovered = listOfPersonsCovered;
        this.numberOfChildren = numberOfChildren;
        this.numberOfAdults = numberOfAdults;
    }

    public PeopleCoveredByFireStationAndNumberOfChildren()
    {
    }
}
