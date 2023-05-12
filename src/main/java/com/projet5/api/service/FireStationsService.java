package com.projet5.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.projet5.api.model.*;
import com.projet5.api.repository.IRepository;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;


@Service
public class FireStationsService {

    private static final Logger logger = LogManager.getLogger("FireStationsService");
    @Autowired
    JSONReaderFromURLIMPL jsonReaderFromURLIMPL;

    //setter used in controller test and IT test
    public void setJsonReaderFromURLIMPL(IRepository repository)
    {
        this.jsonReaderFromURLIMPL= (JSONReaderFromURLIMPL) repository;
    }

    public JSONReaderFromURLIMPL getJsonReaderFromURLIMPL()
    {
        return jsonReaderFromURLIMPL;
    }

    public List<String> getAddressCoveredByFireStation(List<FireStations> listOfFireStationSelected)
    {
        List<String> listOfAddressCoveredBySelectedFireStation = new ArrayList<>();

        for (FireStations fireStation : listOfFireStationSelected)
        {
            String addressCovered = fireStation.getAddress();
            listOfAddressCoveredBySelectedFireStation.add(addressCovered);
        }
        logger.debug("the list of address covered by the fire station was created");

        return listOfAddressCoveredBySelectedFireStation;
    }

    public PeopleCoveredByFireStationAndNumberOfChildren getPersonsCoveredByFireStationNumberAndNumberOfChildren(int stationNumber)
    {
        int numberOfChildrenAtTheSelectedAddress=0;
        int numberOfAdultAtTheSelectedAddress=0;
        List<Persons> listOfPersonsCoveredBySelectedFireStation = new ArrayList<>();

        try
        {
            List<FireStations> listOfFireStationSelected = jsonReaderFromURLIMPL.getFireStationByStationNumber(stationNumber);

            List<String> listOfAddressCoveredBySelectedFireStation = getAddressCoveredByFireStation(listOfFireStationSelected);

            for(String addressCovered : listOfAddressCoveredBySelectedFireStation)
            {
                List<Persons> personsWhoLiveAtTheAddress = jsonReaderFromURLIMPL.getAllPersonsByAddress(addressCovered);


                jsonReaderFromURLIMPL.calculateAgeOfPersons(personsWhoLiveAtTheAddress);

                long childrenCount = personsWhoLiveAtTheAddress.stream().filter(p -> p.getAge() < 18).count();
                numberOfChildrenAtTheSelectedAddress += childrenCount;

                listOfPersonsCoveredBySelectedFireStation.addAll(personsWhoLiveAtTheAddress);
            }

            numberOfAdultAtTheSelectedAddress = listOfPersonsCoveredBySelectedFireStation.size()-numberOfChildrenAtTheSelectedAddress;

            logger.debug("the list of persons covered by fire station selected was created");
        }
        catch(Exception ex){
            logger.error("Error fetching the list of persons covered by fire station selected",ex);
        }
        return new PeopleCoveredByFireStationAndNumberOfChildren(listOfPersonsCoveredBySelectedFireStation, numberOfChildrenAtTheSelectedAddress, numberOfAdultAtTheSelectedAddress);
    }

    public List<Persons> getPersonsCoveredByFireStationNumber(int stationNumber)
    {
        List<Persons> listOfPersonsCoveredByFireStationNumber = new ArrayList<>();

        try
        {
            //create a list with all persons covered by the selected fire station
            List<FireStations> listOfFireStationSelected = jsonReaderFromURLIMPL.getFireStationByStationNumber(stationNumber);

            List<String> listOfAddressCoveredBySelectedFireStation = getAddressCoveredByFireStation(listOfFireStationSelected);

            for (String addressCovered : listOfAddressCoveredBySelectedFireStation) {
                List<Persons> listOfPersonWhoLiveAtThisAddress = jsonReaderFromURLIMPL.getAllPersonsByAddress(addressCovered);

                listOfPersonsCoveredByFireStationNumber.addAll(listOfPersonWhoLiveAtThisAddress);
            }
            logger.debug("the list of persons covered by fire station selected was created");
        }
        catch(Exception ex)
        {
            logger.error("Error fetching the list of persons covered by fire station selected",ex);
        }
        return listOfPersonsCoveredByFireStationNumber;
    }

    public FireStationNumberAndPersonsByAddress getPersonsWhoLiveAtTheAddressAndFireStationWhichCoverThem(String address)
    {
        FireStationNumberAndPersonsByAddress fireStationNumberAndPersonsByAddress = new FireStationNumberAndPersonsByAddress();
        try
        {
            //create the list of persons who live at the address
            List<Persons> listOfPersonsWhoLiveAtTheAddress = jsonReaderFromURLIMPL.getAllPersonsByAddress(address);
            //recover the fire station which cover this address
            int fireStationNumberWhichCoverAddress = jsonReaderFromURLIMPL.getFireStationByAddress(address).getStation();

            //setup the list with age for each person
            jsonReaderFromURLIMPL.calculateAgeOfPersons(listOfPersonsWhoLiveAtTheAddress);

           fireStationNumberAndPersonsByAddress.setListOfPersonWithMedicalRecords(listOfPersonsWhoLiveAtTheAddress);
           fireStationNumberAndPersonsByAddress.setFireStationNumber(fireStationNumberWhichCoverAddress);

            logger.debug("The list of persons who live at address and the fire station which cover them was create");
        }
        catch(Exception ex)
        {
            logger.error("Error fetching the list of persons who live at address and the fire station which cover them",ex);
        }
        return fireStationNumberAndPersonsByAddress;
    }

    public List<List<Persons>> getFamiliesCoveredByFireStationNumber(List<Integer> stations)
    {
        List<List<Persons>> listOfPersonsOfFamiliesCovered = new ArrayList<>();

        for(int stationNumber : stations)
        {

            try {
                List<Persons> listOfPersonsCoveredByStationNumber = getPersonsCoveredByFireStationNumber(stationNumber);
                List<String> listOfLastNameOfFamilies = new ArrayList<>();
                List<String> listOfAddress = new ArrayList<>();

                for (Persons person : listOfPersonsCoveredByStationNumber) {
                    String lastNameOfFamily = person.getLastName();
                    String addressOfPerson = person.getAddress();
                    if (!listOfLastNameOfFamilies.contains(lastNameOfFamily)) {
                        listOfLastNameOfFamilies.add(lastNameOfFamily);
                        List<Persons> listOfPersonsOfSameFamily = new ArrayList<>();
                        listOfPersonsOfSameFamily.add(person);
                        listOfPersonsOfFamiliesCovered.add(listOfPersonsOfSameFamily);
                        listOfAddress.add(addressOfPerson);
                    } else {
                        for (List<Persons> list : listOfPersonsOfFamiliesCovered) {
                            if (list.get(0).getLastName().equals(lastNameOfFamily) && list.get(0).getAddress().equals(person.getAddress())) {
                                list.add(person);
                            }
                            else if(!list.get(0).getLastName().equals(lastNameOfFamily) && list.get(0).getAddress().equals(person.getAddress()))
                            {
                                List<Persons> listOfPersonsOfSameFamily = new ArrayList<>();
                                listOfPersonsOfSameFamily.add(person);
                                listOfPersonsOfFamiliesCovered.add(listOfPersonsOfSameFamily);
                            }
                        }
                    }

                    for (List<Persons> list : listOfPersonsOfFamiliesCovered) {
                        jsonReaderFromURLIMPL.calculateAgeOfPersons(list);
                    }
                }
                logger.debug("the list of families covered by fire station number was created");
            } catch (Exception ex) {
                logger.error("Error fetching the list of families covered by fire station number", ex);
            }
        }
        return listOfPersonsOfFamiliesCovered;
    }

    public boolean addANewFireStation(FireStations fireStation) throws JSONException, JsonProcessingException
    {
        return jsonReaderFromURLIMPL.saveNewFireStation(fireStation);
    }

    public boolean deleteFireStationByStationNumber(int stationNumber) throws JSONException, JsonProcessingException
    {
       return jsonReaderFromURLIMPL.deleteFireStationByStationNumber(stationNumber);
    }

    public boolean deleteFireStationByAddress(String address) throws JSONException, JsonProcessingException
    {
      return  jsonReaderFromURLIMPL.deleteFireStationByAddress(address);
    }

    public boolean upDateStationNumber(String address, int stationNumber) throws JSONException, JsonProcessingException
    {
       return jsonReaderFromURLIMPL.upDateStationNumber(address, stationNumber);
    }

    //used on Postman for control
    public List<FireStations> getAllFireStation() throws JSONException, JsonProcessingException
    {
       return jsonReaderFromURLIMPL.getFireStations();
    }
}
