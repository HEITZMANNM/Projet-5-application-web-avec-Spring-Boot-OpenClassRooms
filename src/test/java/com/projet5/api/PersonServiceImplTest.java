package com.projet5.api;

import com.projet5.api.model.ListOfChildrenAndAdultsByAddress;
import com.projet5.api.model.Persons;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import com.projet5.api.service.PersonsServiceImpl;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PersonServiceImplTest {

    @InjectMocks
    private PersonsServiceImpl personsService = new PersonsServiceImpl();

    private  List<Persons> listOfPersonsByAddress;

    private List<Persons> listOfAllPersons;

    @Mock(lenient = true)
    private  JSONReaderFromURLIMPL jsonReaderFromURLIMPL;


    //creat a list with one child and two adult at the same address which will be used to test the different methods of personService
    @BeforeEach
    private void setUp()
    {
        listOfPersonsByAddress = new ArrayList<>();
        listOfAllPersons = new ArrayList<>();

        try {
            String addressSelected = "11 way of Yellowstone";
            String addressAunt = "15 street of Montana";

            Persons child = new Persons();
            Persons father = new Persons();
            Persons mother = new Persons();
            Persons aunt = new Persons();

            child.setAddress(addressSelected);
            child.setFirstName("Beth");
            child.setLastName("Dutton");
            child.setAge(13);
            child.setCity("MontanaCity");

            father.setAddress(addressSelected);
            father.setFirstName("John");
            father.setLastName("Dutton");
            father.setAge(43);
            father.setCity("MontanaCity");

            mother.setAddress(addressSelected);
            mother.setFirstName("Marta");
            mother.setLastName("Dutton");
            mother.setAge(41);
            mother.setCity("MontanaCity");

            aunt.setAddress(addressAunt);
            aunt.setFirstName("Lisa");
            aunt.setLastName("Dutton");
            aunt.setAge(40);
            aunt.setCity("Havre");

            listOfPersonsByAddress.add(child);
            listOfPersonsByAddress.add(father);
            listOfPersonsByAddress.add(mother);

            listOfAllPersons.add(child);
            listOfAllPersons.add(mother);
            listOfAllPersons.add(father);
            listOfAllPersons.add(aunt);

            when(jsonReaderFromURLIMPL.getAllPersonsByAddress(anyString())).thenReturn(listOfPersonsByAddress);
            when(jsonReaderFromURLIMPL.getPersonByLastName(anyString())).thenReturn(listOfAllPersons);
            when(jsonReaderFromURLIMPL.getPersons()).thenReturn(listOfAllPersons);

        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException("Failed to set up test mock objects");
        }
    }

    //test if method getChildrenAtAddressAndTheOtherMemberOfFamily() return a list  of children and adult who live at the address
    @Test
    public void testTheImplementationOfListOfChildrenByAddress() throws JSONException, IOException {
        String address = "11 way of Yellowstone";

        ListOfChildrenAndAdultsByAddress listExpected = personsService.getChildrenAtAddressAndTheOtherMemberOfFamily(address);

        assertEquals(listExpected.getListOfChildren().size(), 1);
        assertEquals(listExpected.getListOfAdults().size(), 2);
    }

    //Control if the method getPersonInfo() return a list with the Person search in terms of lastName and FirstName
    @Test
    public void testIfTheInfoOfOneSearchPersonIsCorrect()
    {

        String firstNameSearch = "Beth";
        String lastNameSearch = "Dutton";

        List<Persons> listOfPersonSearch = personsService.getPersonInfo(firstNameSearch, lastNameSearch);

        assertEquals(listOfPersonSearch.get(0).getFirstName(), firstNameSearch);
        assertEquals(listOfPersonSearch.get(0).getLastName(), lastNameSearch);
    }

    //Control if the method getAllPersonsByCity return a list of person who live in the same city
    @Test
    public void testTheCorrectSearchOfPersonByCity()
    {
        String citySearch = "MontanaCity";

        List<Persons> listOfPersonsWhoLiveInTheSameCity = personsService.getAllPersonsByCity(citySearch);

        assertEquals(listOfPersonsWhoLiveInTheSameCity.size(), 3);
    }
}
