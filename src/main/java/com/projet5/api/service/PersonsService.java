package com.projet5.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.projet5.api.model.*;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Data
@Service
public class PersonsService {

    private static final Logger logger = LogManager.getLogger("PersonsService");

    @Autowired
    JSONReaderFromURLIMPL jsonReaderFromURLIMPL;


    public ListOfChildrenAndAdultsByAddress getChildrenAtAddressAndTheOtherMemberOfFamily(String address) throws JSONException, IOException {
        try {
            //create the list of adults who live at the same address
            List<Persons> listOfPersonsByAddress = jsonReaderFromURLIMPL.getAllPersonsByAddress(address);

            //calculate the age of each persons
           jsonReaderFromURLIMPL.calculateAgeOfPersons(listOfPersonsByAddress);

            List<Persons> listOfChildren = new ArrayList<>();

            List<Persons> listOfAdult = new ArrayList<>();

            for (Persons person : listOfPersonsByAddress)
            {
                if(person.getAge() < 18)
                {
                    listOfChildren.add(person);
                }
                else {
                    listOfAdult.add(person);
                }
            }
            ListOfChildrenAndAdultsByAddress listOfChildrenAndAdultsByAddress = new ListOfChildrenAndAdultsByAddress(listOfChildren, listOfAdult);

            return listOfChildrenAndAdultsByAddress;
        }
        catch (Exception ex) {
            logger.error("Error fetching the list of children and adult by address", ex);
        }
        return null;
    }


public List<Persons> getPersonInfo(String firstName, String lastName) {
    List<Persons> listOfPersonsByLastName = jsonReaderFromURLIMPL.getPersonByLastName(lastName);
    List<Persons> listOfPersonSearch = new ArrayList<>();

    try {

        jsonReaderFromURLIMPL.calculateAgeOfPersons(listOfPersonsByLastName);

        for(Persons person : listOfPersonsByLastName)
        {
            String firstNamePerson = person.getFirstName();

            if(firstName.equals(firstNamePerson))
            {
                listOfPersonSearch.add(person);
            }
        }
        logger.debug("The person's info search was completed ");
    }
    catch (Exception ex) {
        logger.error("Error fetching the list of  persons by their firstName", ex);
    }
    return listOfPersonSearch;
}

public List<Persons> getAllPersonsByCity(String city)
{
    try
    {
        List<Persons> listOfAllPersons = jsonReaderFromURLIMPL.getPersons();
        List<Persons> listOfPersonsWhoLiveInTheSelectedCity = new ArrayList<>();

        for(Persons person : listOfAllPersons)
        {
            String cityWhereLiveThePerson = person.getCity();

            if(city.equals(cityWhereLiveThePerson))
            {
                listOfPersonsWhoLiveInTheSelectedCity.add(person);
            }
        }
return listOfPersonsWhoLiveInTheSelectedCity;
    }
    catch (Exception ex)
    {
        logger.error("Error fetching the list of  phone number by city", ex);
    }
    return null;
}

public void addANewPerson(Persons person)
{
    jsonReaderFromURLIMPL.saveNewPerson(person);
}

public void deleteThePerson(String firstName, String lastName)
{
    jsonReaderFromURLIMPL.deletePerson(firstName, lastName);
}

public void upDatePerson(Persons person)
{
    jsonReaderFromURLIMPL.upDatePersonInfo(person);
}



}




