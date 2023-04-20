package com.projet5.api;

import com.projet5.api.model.ListOfChildrenAndAdultsByAddress;
import com.projet5.api.model.MedicalRecords;
import com.projet5.api.model.Persons;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import com.projet5.api.service.PersonsService;
import org.json.JSONException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    private static PersonsService personsService;

    private static List<Persons> listOfPersons;

    @Mock
    private static JSONReaderFromURLIMPL jsonReaderFromURLIMPL;

    //creat a list with one child and two adult at the same adress
    @BeforeAll
    private static void setUp()
    {
        listOfPersons = new ArrayList<>();

        try {



            String addressSelected = "11 way of Yellowstone";

            Persons child = new Persons();
            Persons father = new Persons();
            Persons mother = new Persons();

            child.setAddress(addressSelected);
            child.setFirstName("Beth");
            child.setLastName("Dutton");
            MedicalRecords medicalRecordsChild = new MedicalRecords();
            medicalRecordsChild.setBirthdate("10/10/2010");
            child.setMedicalRecords(medicalRecordsChild);


            father.setAddress(addressSelected);
            father.setFirstName("John");
            father.setLastName("Dutton");
            MedicalRecords medicalRecordsFather = new MedicalRecords();
            medicalRecordsFather.setBirthdate("15/10/1975");
            father.setMedicalRecords(medicalRecordsFather);

            mother.setAddress(addressSelected);
            mother.setFirstName("Marta");
            mother.setLastName("Dutton");
            MedicalRecords medicalRecordsMother = new MedicalRecords();
            medicalRecordsMother.setBirthdate("15/12/1978");
            mother.setMedicalRecords(medicalRecordsMother);


            listOfPersons.add(child);
            listOfPersons.add(father);
            listOfPersons.add(mother);



            when(jsonReaderFromURLIMPL.getAllPersonsByAddress(anyString())).thenReturn(listOfPersons);
            personsService= new PersonsService();
            personsService.setJsonReaderFromURLIMPL(jsonReaderFromURLIMPL);

        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("Failed to set up test mock objects");
        }



    }

//    @AfterAll
//    private static void clearList()
//    {
//        personsService.deleteThePerson("Beth", "Dutton");
//        personsService.deleteThePerson("John", "Dutton");
//        personsService.deleteThePerson("Marta", "Dutton");
//    }

    //test if method getChildrenAtAddressAndTheOtherMemberOfFamily() return a list  of children and adult who live at the address
    @Test
    public void testTheImplementationOfListOfChildrenByAddress() throws JSONException, IOException {
        String address = "11 way of Yellowstone";

       ListOfChildrenAndAdultsByAddress listExpected = personsService.getChildrenAtAddressAndTheOtherMemberOfFamily(address);

        assertEquals(listExpected.getListOfChildren().size(), 1);
        assertEquals(listExpected.getListOfAdults().size(), 2);

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
