package com.projet5.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.projet5.api.model.ListOfChildrenAndAdultsByAddress;
import com.projet5.api.model.Persons;
import com.projet5.api.model.View;
import com.projet5.api.service.PersonsService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
public class PersonsController {

    @Autowired
    private PersonsService personsService;

    @JsonView(View.ChildrenByAddressAndOtherMemberOfFamily.class)
    @GetMapping("/childAlert")
    public ListOfChildrenAndAdultsByAddress getChildrenAtTheAddressAndOtherMemberOfFamily(@RequestParam(name = "address") String address) throws JSONException, IOException {
        return personsService.getChildrenAtAddressAndTheOtherMemberOfFamily(address);
    }

    @JsonView(View.PersonInfo.class)
    @GetMapping("/personInfo")
    public List<Persons> getPersonInfo(@RequestParam(name = "firstName") String firstname, @RequestParam(name = "lastName") String lastName) {
        return personsService.getPersonInfo(firstname, lastName);
    }

    @JsonView(View.Email.class)
    @GetMapping("/communityEmail")
    public List<Persons> getEmailOfPersonsWhoLiveAtSelectedCity(@RequestParam(name = "city") String city) {
        return personsService.getAllPersonsByCity(city);
    }

    @PostMapping("/person")
    public ResponseEntity<Void> postNewPerson(@RequestBody Persons person)
    {
        personsService.addANewPerson(person);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/personInfo?firstName={}&lastName={}")
                .buildAndExpand(person.getFirstName(), person.getLastName())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/person")
    public ResponseEntity<HttpStatus> deletePerson(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName)
    {
        personsService.deleteThePerson(firstName, lastName);

        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }
    @PutMapping("/person")
    public Persons putPerson(@RequestBody Persons person)
    {
        personsService.updatePerson(person);

        return person;
    }

    public void setPersonsService(PersonsService personsService) {
        this.personsService = personsService;
    }
}