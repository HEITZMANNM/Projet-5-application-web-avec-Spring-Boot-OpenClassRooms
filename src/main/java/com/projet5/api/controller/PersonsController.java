package com.projet5.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.projet5.api.model.ListOfChildrenAndAdultsByAddress;
import com.projet5.api.model.Persons;
import com.projet5.api.model.View;
import com.projet5.api.service.PersonsService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public ResponseEntity<HttpStatus> postNewPerson(@RequestBody Persons person)
    {
       if(personsService.addANewPerson(person))
       {
           return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
       }
        return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/person")
    public ResponseEntity<HttpStatus> deletePerson(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName)
    {
      if(personsService.deleteThePerson(firstName, lastName))
      {
          return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
      }
        return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);

    }
    @PutMapping("/person")
    public ResponseEntity<HttpStatus>  putPerson(@RequestBody Persons person)
    {
       if(personsService.updatePerson(person))
       {
           return new ResponseEntity<HttpStatus>(HttpStatus.OK);
       }

        return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
    }

    public void setPersonsService(PersonsService personsService) {
        this.personsService = personsService;
    }
}