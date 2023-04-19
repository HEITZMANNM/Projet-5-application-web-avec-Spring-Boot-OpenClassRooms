package com.projet5.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.projet5.api.model.ListOfChildrenAndAdultsByAddress;
import com.projet5.api.model.PersonAndPeopleWithSameLastName;
import com.projet5.api.model.Persons;
import com.projet5.api.model.View;
import com.projet5.api.service.PersonsService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class PersonsController {

    @Autowired
    private PersonsService personsService;

    @JsonView(View.ChildrenByAddressAndOtherMemberOfFamily.class)
    @GetMapping("/childAlert")
    public ListOfChildrenAndAdultsByAddress getChildrenAtTheAddressAndOtherMemberOfFamily(@RequestParam(name = "address") String address) throws JSONException, IOException
    {
        return personsService.getChildrenAtAddressAndTheOtherMemberOfFamily(address);
    }

    @JsonView(View.PersonSearchAndPersonsWithSameLastName.class)
    @GetMapping("/personInfo")
    public PersonAndPeopleWithSameLastName getPersonAndPeopleWithSameLastName(@RequestParam(name= "firstName") String firstname, @RequestParam(name= "lastName") String lastName)
    {
        return personsService.getPersonByFirstNameAndLastName(firstname, lastName);
    }

    @JsonView(View.Email.class)
    @GetMapping("/communityEmail")
    public List<Persons> getEmailOfPersonsWhoLiveAtSelectedCity(@RequestParam(name = "city") String city)
    {
        return personsService.getAllPersonsByCity(city);
    }

    @PostMapping("/person")
    public void postNewPerson(@RequestBody Persons person)
    {
        personsService.saveNewPerson(person);
    }
}
