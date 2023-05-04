package com.projet5.api.repository;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.projet5.api.model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Reader;

import java.util.*;


public interface IRepository {

    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException;
    public String readAll(Reader rd) throws IOException;
    public JSONArray getPersonsJson() throws JSONException;
    public List<Persons> getPersons() throws JSONException, JsonProcessingException;
    public List<Persons> getPersonByLastName(String lastName) throws JSONException, JsonProcessingException;
    public FireStations getFireStationByAddress(String address) throws JSONException, JsonProcessingException;
    public JSONArray getMedicalRecordsJson() throws JSONException;
    public List<MedicalRecords> getMedicalRecords();
    public List<MedicalRecords> getMedicalRecordsByAddress(String address);
    public List<Persons> getAllPersonsByAddress(String address) throws JSONException, JsonProcessingException;
    public JSONArray getFireStationsJson() throws JSONException;
    public List<FireStations> getFireStations() throws JSONException, JsonProcessingException;
    public List<FireStations> getFireStationByStationNumber(int stationNumber) throws JSONException, JsonProcessingException;
    public void calculateAgeOfPersons(List<Persons> listOfPersons);
    public boolean saveNewPerson(Persons person);

    public boolean deletePerson(String firstName, String lastName);

    public boolean upDatePersonInfo (Persons person);

    public boolean saveNewFireStation(FireStations fireStation) throws JSONException, JsonProcessingException;

    public boolean deleteFireStationByAddress(String address) throws JSONException, JsonProcessingException;

    public boolean deleteFireStationByStationNumber(int stationNumber) throws JSONException, JsonProcessingException;

    public boolean upDateStationNumber(String address, int stationNumber) throws JSONException, JsonProcessingException;

    public boolean saveNewMedicalRecords(MedicalRecords medicalRecord);

    public boolean upDateMedicalRecords(MedicalRecords medicalRecord);

    public boolean deleteMedicalRecords(String firstName, String lasteName);
}




