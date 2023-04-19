package com.projet5.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.List;

@Data
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
}
