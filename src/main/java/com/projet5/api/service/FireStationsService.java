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

import java.util.ArrayList;

import java.util.List;

@Data
@Service
public class FireStationsService {

    private static final Logger logger = LogManager.getLogger("FireStationsService");
    @Autowired
    JSONReaderFromURLIMPL jsonReaderFromURLIMPL;

    public List<String> getAddressCoveredByFireStation(List<FireStations> listOfFireStationSelected)
    {
        List<String> listOfAddressCoveredBySelectedFireStation = new ArrayList<>();
        for (FireStations fireStation : listOfFireStationSelected)
        {
            String addressCovered = fireStation.getAddress();
            listOfAddressCoveredBySelectedFireStation.add(addressCovered);
        }

        return listOfAddressCoveredBySelectedFireStation;
    }

    public PeopleCoveredByFireStationAndNumberOfChildren getPersonsCoveredByFireStationNumberAndNumberOfChildren(int stationNumber) {

        int numberOfChildrenAtTheSelectedAddress=0;
        int numberOfAdultAtTheSelectedAddress=0;
        List<Persons> listOfPersonsCoveredBySelectedFireStation = new ArrayList<>();

        try{
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

        }
        catch(Exception ex){
            logger.error("Error fetching the list of persons covered by fire station selected",ex);
        }
        return new PeopleCoveredByFireStationAndNumberOfChildren(listOfPersonsCoveredBySelectedFireStation, numberOfChildrenAtTheSelectedAddress, numberOfAdultAtTheSelectedAddress);
    }

    public List<Persons> getPersonsCoveredByFireStationNumber(int stationNumber){

        List<Persons> listOfPersonsCoveredByFireStationNumber = new ArrayList<>();

        try {
            //create a list with all persons covered by the selected fire station
            List<FireStations> listOfFireStationSelected = jsonReaderFromURLIMPL.getFireStationByStationNumber(stationNumber);

            List<String> listOfAddressCoveredBySelectedFireStation = getAddressCoveredByFireStation(listOfFireStationSelected);

            for (String addressCovered : listOfAddressCoveredBySelectedFireStation) {
                List<Persons> listOfPersonWhoLiveAtThisAddress = jsonReaderFromURLIMPL.getAllPersonsByAddress(addressCovered);

                listOfPersonsCoveredByFireStationNumber.addAll(listOfPersonWhoLiveAtThisAddress);
            }
        }
        catch(Exception ex)
        {
            logger.error("Error fetching the list of persons covered by fire station selected",ex);
        }
        return listOfPersonsCoveredByFireStationNumber;
    }

    public FireStationNumberAndPersonsByAddress getPersonsWhoLiveAtTheAddressAndFireStationWhichCoverThem(String address)
    {
        try
        {
            //create the list of persons who live at the address
            List<Persons> listOfPersonsWhoLiveAtTheAddress = jsonReaderFromURLIMPL.getAllPersonsByAddress(address);
            //recover the fire station which cover this address
            int fireStationNumberWhichCoverAddress = jsonReaderFromURLIMPL.getFireStationByAddress(address).getStation();


            //setup the list with age for each person
            jsonReaderFromURLIMPL.calculateAgeOfPersons(listOfPersonsWhoLiveAtTheAddress);

            FireStationNumberAndPersonsByAddress fireStationNumberAndPersonsByAddress = new FireStationNumberAndPersonsByAddress(listOfPersonsWhoLiveAtTheAddress, fireStationNumberWhichCoverAddress);

            return fireStationNumberAndPersonsByAddress;
        }
        catch(Exception ex)
        {
            logger.error("Error fetching the list of persons who live at address and the fire station which cover them",ex);
        }
        return null;
    }

    public List<List<Persons>> getFamiliesCoveredByFireStationNumber(int stationNumber)
    {
        try
        {
            List<Persons> listOfPersonsCoveredByStationNumber = getPersonsCoveredByFireStationNumber(stationNumber);
            List<String> listOfLastNameOfFamilies = new ArrayList<>();

            List<List<Persons>> listOfPersonsOfFamiliesCovered = new ArrayList<>();

            for (Persons person : listOfPersonsCoveredByStationNumber)
            {
                String lastNameofFamily = person.getLastName();
                if (!listOfLastNameOfFamilies.contains(lastNameofFamily))
                {
                    listOfLastNameOfFamilies.add(lastNameofFamily);
                    List<Persons> listOfPersonsOfSameFamily = new ArrayList<>();
                    listOfPersonsOfSameFamily.add(person);
                    listOfPersonsOfFamiliesCovered.add(listOfPersonsOfSameFamily);
                }
                else
                {
                    for(List<Persons> list : listOfPersonsOfFamiliesCovered)
                    {
                        if (list.get(0).getLastName().equals(lastNameofFamily))
                        {
                            list.add(person);
                        }
                    }
                }

                for(List<Persons> list : listOfPersonsOfFamiliesCovered)
                {
                    jsonReaderFromURLIMPL.calculateAgeOfPersons(list);
                }
            }
return listOfPersonsOfFamiliesCovered;
        }
        catch(Exception ex)
        {
            logger.error("Error fetching the list of families covered by fire station number",ex);
        }
        return null;
    }

    public void addANewFireStation(FireStations fireStation) throws JSONException, JsonProcessingException {
        jsonReaderFromURLIMPL.saveNewFireStation(fireStation);
    }

    public void deleteFireStation(String address, int sattionNumber) throws JSONException, JsonProcessingException {
        jsonReaderFromURLIMPL.deleteFireStation(sattionNumber, address);
    }

    public void upDateStationNumber(String address, int stationNumber) throws JSONException, JsonProcessingException {
        jsonReaderFromURLIMPL.upDateStationNumber(address, stationNumber);
    }





//Pour Tester
    public List<FireStations> getAllFireStation() throws JSONException, JsonProcessingException {
        return jsonReaderFromURLIMPL.getFireStations();
    }



}
