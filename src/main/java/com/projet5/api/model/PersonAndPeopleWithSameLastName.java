package com.projet5.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.List;

@Data
public class PersonAndPeopleWithSameLastName {

    @JsonView(View.PersonSearch.class)
    private Persons person;

    @JsonView(View.PersonFirstNameAndLastNameOnly.class)
    private List<Persons> listOfOtherPeopleWithSameLastName;

    public PersonAndPeopleWithSameLastName(Persons person, List<Persons> listOfOtherPeopleWithSameLastName)
    {
        this.person = person;
        this.listOfOtherPeopleWithSameLastName = listOfOtherPeopleWithSameLastName;
    }

    public PersonAndPeopleWithSameLastName()
    {
    }
}
