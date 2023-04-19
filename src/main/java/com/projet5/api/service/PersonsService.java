package com.projet5.api.service;

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
            jsonReaderFromURLIMPL.setPersonsMedicalRecords(listOfPersonsByAddress);
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


public PersonAndPeopleWithSameLastName getPersonByFirstNameAndLastName(String firstName, String lastName)
{
    try {
        List<Persons> listOfPersonsByLastName = jsonReaderFromURLIMPL.getPersonByLastName(lastName);
        jsonReaderFromURLIMPL.setPersonsMedicalRecords(listOfPersonsByLastName);
        jsonReaderFromURLIMPL.calculateAgeOfPersons(listOfPersonsByLastName);
        PersonAndPeopleWithSameLastName personAndPeopleWithSameLastName = new PersonAndPeopleWithSameLastName();

        int indexOfSearchedPerson = 0;

        for (Persons person : listOfPersonsByLastName) {
            String firstNamePerson = person.getFirstName();

            if (firstNamePerson.equals(firstName)) {
                personAndPeopleWithSameLastName.setPerson(person);
                indexOfSearchedPerson = listOfPersonsByLastName.indexOf(person);
            }
        }
        listOfPersonsByLastName.remove(indexOfSearchedPerson);
        personAndPeopleWithSameLastName.setListOfOtherPeopleWithSameLastName(listOfPersonsByLastName);
        return personAndPeopleWithSameLastName;
    }
    catch (Exception ex) {
        logger.error("Error fetching the list of  persons by their firstName", ex);
    }
    return null;
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

    public void saveNewPerson(Persons person)
    {
        try
        {
            //create the URL
            URL url = new URL ("https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json");
            //open connection
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

//            //convert object Persons into String and create the request body
            String jsonInputString = jsonReaderFromURLIMPL.convertPersonToJson(person);

            try(OutputStream os = con.getOutputStream())
            {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }
        }
        catch (Exception ex)
        {
            logger.error("Error save the person", ex);
        }
    }

//    public Persons editPersonInfo(Persons person)
//    {
//
//    }
}




