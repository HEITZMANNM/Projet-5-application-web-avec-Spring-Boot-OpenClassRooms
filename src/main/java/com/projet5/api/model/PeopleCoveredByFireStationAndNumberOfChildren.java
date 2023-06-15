package com.projet5.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PeopleCoveredByFireStationAndNumberOfChildren that = (PeopleCoveredByFireStationAndNumberOfChildren) o;
        return numberOfChildren == that.numberOfChildren && numberOfAdults == that.numberOfAdults && Objects.equals(listOfPersonsCovered, that.listOfPersonsCovered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listOfPersonsCovered, numberOfChildren, numberOfAdults);
    }
}
