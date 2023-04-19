package com.projet5.api;

import com.projet5.api.model.Persons;
import com.projet5.api.service.PersonsService;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PersonServiceTest {

    private static PersonsService personsService;
    @BeforeAll
    private static void setUp() {
        personsService = new PersonsService();
    }

////    test if the method getPersons(), return a completed List of Persons
//    @Test
//    public void getPersonsTest() throws JSONException, IOException {
//        List<Persons> listOfPersonsExpected = personsService.getPersons();
//        assertNotNull(listOfPersonsExpected);
//    }

    //observe if the method getPersonByFirstName(), retun the expected Person call John Boyd in the DB
//    @Test
//    public void getPersonByFirstnameTest() throws JSONException, IOException {
//        String firstName = "John";
//        String lastName = "Boyd";
//        Persons personExpected = personsService.getPersonByFirstname(firstName);
//
//        assertEquals(personExpected.getLastName(), lastName);
//    }
//
//    //test if the method getPersonsByAddress(), return a complete list of peoples and if these people live at the good address
//    @Test
//    public void getPersonByAddressTest() throws JSONException, IOException {
//        String addressTarget = "1509 Culver St";
//        List<Persons> listOfPersonsWhoLiveAtTheTargetAddress = personsService.getPersonByAddress(addressTarget);
//
//        Persons personAtTheIndex0 = listOfPersonsWhoLiveAtTheTargetAddress.get(0);
//        String addressOfThePersonAtTheIndexOOfTheList = personAtTheIndex0.getAddress();
//
//        assertNotNull(listOfPersonsWhoLiveAtTheTargetAddress);
//        assertEquals(addressTarget, addressOfThePersonAtTheIndexOOfTheList);
//    }

}
