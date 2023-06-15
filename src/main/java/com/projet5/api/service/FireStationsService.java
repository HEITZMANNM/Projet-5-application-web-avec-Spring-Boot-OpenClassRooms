package com.projet5.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.projet5.api.model.FireStationNumberAndPersonsByAddress;
import com.projet5.api.model.FireStations;
import com.projet5.api.model.PeopleCoveredByFireStationAndNumberOfChildren;
import com.projet5.api.model.Persons;
import com.projet5.api.repository.IRepository;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import org.json.JSONException;

import java.util.List;

public interface FireStationsService {

    public void setJsonReaderFromURLIMPL(IRepository repository);

    public JSONReaderFromURLIMPL getJsonReaderFromURLIMPL();

    public List<String> getAddressCoveredByFireStation(List<FireStations> listOfFireStationSelected);

    public PeopleCoveredByFireStationAndNumberOfChildren getPersonsCoveredByFireStationNumberAndNumberOfChildren(int stationNumber);

    public List<Persons> getPersonsCoveredByFireStationNumber(int stationNumber);

    public FireStationNumberAndPersonsByAddress getPersonsWhoLiveAtTheAddressAndFireStationWhichCoverThem(String address);

    public List<List<Persons>> getFamiliesCoveredByFireStationNumber(List<Integer> stations);

    public boolean addANewFireStation(FireStations fireStation) throws JSONException, JsonProcessingException;

    public boolean deleteFireStationByStationNumber(int stationNumber) throws JSONException, JsonProcessingException;

    public boolean deleteFireStationByAddress(String address) throws JSONException, JsonProcessingException;

    public boolean upDateStationNumber(String address, int stationNumber) throws JSONException, JsonProcessingException;

    public List<FireStations> getAllFireStation() throws JSONException, JsonProcessingException;
}
