package com.projet5.api.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet5.api.model.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Reader;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.*;


public interface IRepository {

    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException;
    public String readAll(Reader rd) throws IOException;
    public JSONArray getPersonsJson();
    public List<Persons> getPersons() throws JSONException, JsonProcessingException;
    public List<Persons> getPersonByLastName(String lastName) throws JSONException, JsonProcessingException;
    public FireStations getFireStationByAddress(String address) throws JSONException, JsonProcessingException;
    public JSONArray getMedicalRecordsJson();
    public List<MedicalRecords> getMedicalRecords();
    public List<MedicalRecords> getMedicalRecordsByAddress(String address);
    public List<Persons> getAllPersonsByAddress(String address) throws JSONException, JsonProcessingException;
    public JSONArray getFireStationsJson();
    public List<FireStations> getFireStations() throws JSONException, JsonProcessingException;
    public List<FireStations> getFireStationByStationNumber(int stationNumber) throws JSONException, JsonProcessingException;
    public List<Persons> calculateAgeOfPersons(List<Persons> listOfPersons);
    public void saveNewPerson(Persons person);

    public void deletePerson(String firstName, String lastName);

    public void upDatePersonInfo (Persons person);

    public void saveNewFireStation(FireStations fireStation) throws JSONException, JsonProcessingException;

    public void deleteFireStation(int stationNumber, String address) throws JSONException, JsonProcessingException;

    public void upDateStationNumber(String address, int stationNumber) throws JSONException, JsonProcessingException;

    public void saveNewMedicalRecords(MedicalRecords medicalRecord);

    public void upDateMedicalRecords(MedicalRecords medicalRecord);

    public void deleteMedicalRecords(String firstName, String lasteName);
}




