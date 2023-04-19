package com.projet5.api.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet5.api.model.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;


@Repository
public class JSONReaderFromURLIMPL implements IRepository{

    private static final Logger logger = LogManager.getLogger("JSONReaderFromURLIMPL");

    @Override
    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            return new JSONObject(jsonText);
        } finally {
            is.close();
        }
    }

    @Override
    public String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    @Override
    public JSONArray getPersonsJson() {
        try {
            String url = "https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json";
            JSONObject jsonObject = readJsonFromUrl(url);
            return jsonObject.getJSONArray("persons");

        }
        catch (Exception ex) {
            logger.error("Error fetching the Json list of Person", ex);
        }

        return null;
    }

    @Override
    public List<Persons> getPersons() throws JSONException, JsonProcessingException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Persons> listOfAllPersons = new ArrayList<>();
            JSONArray jsonArray = getPersonsJson();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonPerson = jsonArray.getJSONObject(i);
                String personConvertToString = jsonPerson.toString();
                Persons personObject = objectMapper.readValue(personConvertToString, Persons.class);
                listOfAllPersons.add(personObject);
            }

            return listOfAllPersons;
        }
        catch (Exception ex) {
            logger.error("Error fetching the list of Person", ex);
        }
        return null;
    }

    public List<Persons> setPersonsMedicalRecords(List<Persons> listOfPersons)
    {
        try{
            List<MedicalRecords> listOfMedicalRecords = getMedicalRecords();

            for(MedicalRecords medicalRecord : listOfMedicalRecords)
            {
                String medicalRecordFirstName = medicalRecord.getFirstName();
                String medicalRecordLastName = medicalRecord.getLastName();

                listOfPersons.stream().filter(p -> p.getFirstName().equals(medicalRecordFirstName) && p.getLastName().equals(medicalRecordLastName)).
                        forEach(p -> p.setMedicalRecords(medicalRecord));
            }
return listOfPersons;
        }
        catch (Exception ex)
        {
            logger.error("Error fetching the list of Person with their medical records", ex);
        }
        return null;
    }

    @Override
    public List<Persons> calculateAgeOfPersons(List<Persons> listOfPersons)
    {
        try
        {
for (Persons person : listOfPersons)
{
    Date nowDate = new Date();
    String birthdateString = person.getMedicalRecords().getBirthdate();
    Date birthdateDate = new SimpleDateFormat("dd/MM/yyyy").parse(birthdateString);

    Double ageInDouble = (nowDate.getTime()-birthdateDate.getTime())/3.154e+10;
    int age =  ageInDouble.intValue();

    person.setAge(age);
}
        }
        catch (Exception ex)
        {
            logger.error("Error calculation for the age of each persons", ex);
        }
        return null;
    }
    @Override
    public List<Persons> getPersonByLastName(String lastName) throws JSONException, JsonProcessingException {
        List<Persons> listOfAllPersons = getPersons();
        List<Persons> listOfPersonsWithTheSameLastName = new ArrayList<>();
        try {
            for (Persons person : listOfAllPersons) {
                if (lastName.equals(person.getLastName())) {
                    listOfPersonsWithTheSameLastName.add(person);
                }
            }
            return listOfPersonsWithTheSameLastName;
        } catch (Exception ex) {
            logger.error("Error fetching the list of persons with same firstName", ex);
        }
        return null;
    }

    @Override
    public List<Persons> getAllPersonsByAddress(String address) throws JSONException, JsonProcessingException {
        List<Persons> listOfAllPersons = getPersons();
        List<Persons> listOfPersonByAddress = new ArrayList<>();
        try {
            for (Persons person : listOfAllPersons) {
                if (address.equals(person.getAddress())) {
                    listOfPersonByAddress.add(person);
                }
            }
            return listOfPersonByAddress;
        } catch (Exception ex) {
            logger.error("Error fetching the persons by address", ex);
        }
        return null;
    }

    @Override
    public JSONArray getMedicalRecordsJson() {
        try {
            String url = "https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json";
            JSONObject jsonObject = readJsonFromUrl(url);
            return jsonObject.getJSONArray("medicalrecords");

        }
        catch(Exception ex){
            logger.error("Error fetching the list of JSON Medical Records",ex);
        }

        return null;
    }

    @Override
    public List<MedicalRecords> getMedicalRecords() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<MedicalRecords> listOfAllMedicalRecords = new ArrayList<>();
            JSONArray jsonArray = getMedicalRecordsJson();

//        for(Object jsonMedicalRecord : jsonArray)
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonMedicalRecord = jsonArray.getJSONObject(i);
                String medicalRecordConvertToString = jsonMedicalRecord.toString();
                MedicalRecords medicalRecordObject = objectMapper.readValue(medicalRecordConvertToString, MedicalRecords.class);
                listOfAllMedicalRecords.add(medicalRecordObject);
            }

            return listOfAllMedicalRecords;
        }
        catch(Exception ex){
            logger.error("Error fetching the list of Medical Records",ex);
        }
        return null;
    }
    @Override
    public JSONArray getFireStationsJson() {
        try {
            String url = "https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json";
            JSONObject jsonObject = readJsonFromUrl(url);
            return jsonObject.getJSONArray("firestations");
        }
        catch(Exception ex){
            logger.error("Error fetching the list of Fire Stations",ex);
        }

        return null;
    }

    @Override
    public List<FireStations> getFireStations() throws JSONException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<FireStations> listOfAllFireStations = new ArrayList<>();
        JSONArray jsonArray = getFireStationsJson();

        for(int i=0; i<jsonArray.length(); i++)
//        for(Object jsonPerson : jsonArray)
        {
            JSONObject jsonFireStation = jsonArray.getJSONObject(i);
            String fireStationConvertToString = jsonFireStation.toString();
            FireStations fireStationObject = objectMapper.readValue(fireStationConvertToString, FireStations.class);
            listOfAllFireStations.add(fireStationObject);
        }

        return listOfAllFireStations;
    }

    @Override
    public FireStations getFireStationByAddress(String address) throws JSONException, JsonProcessingException {
        //create the list of all fire stations
        List<FireStations> listOfAllFireStations = getFireStations();

        try {
            for (FireStations fireStation : listOfAllFireStations) {
                if (fireStation.getAddress().equals(address)) {
                    return fireStation;
                }
            }
        } catch (Exception ex) {
            logger.error("Error fetching the Fire Station which cover the address", ex);
        }

        return null;
    }


    @Override
    public List<MedicalRecords> getMedicalRecordsByAddress(String address) {
        try {
            //crete list of all persons which live in the address
            List<Persons> listOfPersonsWhoLiveInAddress = getAllPersonsByAddress(address);
            //crete list of all medical records
            List<MedicalRecords> listOfAllMedicalRecords = getMedicalRecords();
            //crete the list of medical records of the address
            List<MedicalRecords> listOfMedicalRecordsByAddress = new ArrayList<>();

            for (Persons person : listOfPersonsWhoLiveInAddress) {
                String personFirstName = person.getFirstName();
                String personLastName = person.getLastName();

                for (MedicalRecords personMedicalRecord : listOfAllMedicalRecords) {
                    String medicalRecordFirstName = personMedicalRecord.getFirstName();
                    String medicalRecordLastName = personMedicalRecord.getLastName();

                    if (personFirstName.equals(medicalRecordFirstName) && personLastName.equals(medicalRecordLastName)) {
                        listOfMedicalRecordsByAddress.add(personMedicalRecord);
                    }
                }
            }
            return listOfMedicalRecordsByAddress;
        }
        catch(Exception ex){
            logger.error("Error fetching the list of Medical Records by address",ex);
        }
        return null;
    }

    @Override
    public List<FireStations> getFireStationByStationNumber(int stationNumber){

        try {

            List<FireStations> listOfAllFireStation = getFireStations();
            List<FireStations> listOfFireStationsByStationNumber = new ArrayList<>();

            for(FireStations fireStation : listOfAllFireStation)
            {
                int fireStationNumber = fireStation.getStation();

                if(fireStationNumber == stationNumber)
                {
                    listOfFireStationsByStationNumber.add(fireStation);
                }
            }

            return listOfFireStationsByStationNumber;

        }
        catch(Exception ex){
            logger.error("Error fetching the list of FireStations by station Number",ex);
        }
        return null;
    }




        // Main driver method
        public String convertPersonToJson(Persons person)
        {
//            // Creating object of Organisation
//            Organisation org = new Organisation();
//
//            // Insert the data into the object
//            org = getObjectData(org);

            // Creating Object of ObjectMapper define in Jackson
            // Api
            ObjectMapper Obj = new ObjectMapper();

            // Try block to check for exceptions
            try {

                // Getting organisation object as a json string
                String personJsonStr = Obj.writeValueAsString(person);

                // Displaying JSON String on console
                System.out.println(personJsonStr);

                return personJsonStr;
            }

            // Catch block to handle exceptions
            catch (IOException e) {

                // Display exception along with line number
                // using printStackTrace() method
                e.printStackTrace();
            }
            return null;
        }

//        // Method
//        // Getting the data to be inserted
//        // into the object
//        public static Organisation
//        getObjectData(Organisation org)
//        {
//
//            // Insert the custom data
//            org.setName("GeeksforGeeks");
//            org.setDescription(
//                    "A computer Science portal for Geeks");
//            org.setEmployees(2000);
//
//            // Returning the object
//            return org;
//        }
}
