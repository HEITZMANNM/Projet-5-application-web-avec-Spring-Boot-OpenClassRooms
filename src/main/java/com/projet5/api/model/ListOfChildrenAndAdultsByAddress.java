package com.projet5.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class ListOfChildrenAndAdultsByAddress {

    @JsonView(View.ChildrenByAddressAndOtherMemberOfFamily.class)
    private List<Persons> listOfChildren;

@JsonView(View.ChildrenByAddressAndOtherMemberOfFamily.class)
    private List<Persons> listOfAdults;

    public ListOfChildrenAndAdultsByAddress(List<Persons> listOfChildren, List<Persons> listOfAdults)
    {
        this.listOfAdults = listOfAdults;
        this.listOfChildren = listOfChildren;
    }

    public ListOfChildrenAndAdultsByAddress()
    {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListOfChildrenAndAdultsByAddress that = (ListOfChildrenAndAdultsByAddress) o;
        return Objects.equals(listOfChildren, that.listOfChildren) && Objects.equals(listOfAdults, that.listOfAdults);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listOfChildren, listOfAdults);
    }
}
