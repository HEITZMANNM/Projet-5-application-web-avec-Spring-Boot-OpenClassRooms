package com.projet5.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.List;

@Data
public class PeopleCoveredByFireStationAndNumberOfChildren {

@JsonView(View.PeopleCoveredByFireStationNumber.class)
    List<Persons> listOfPersonsCovered;
    @JsonView(View.NumberOfChildren.class)
    int numberOfChildren;

    @JsonView(View.NumberOfAdults.class)
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
