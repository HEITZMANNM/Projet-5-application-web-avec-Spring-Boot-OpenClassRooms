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

    private List<Persons> listOfAllPersons = new ArrayList<>();

    private List<FireStations> listOfAllFireStations = new ArrayList<>();

    private List<MedicalRecords> listOfAllMedicalRecords = new ArrayList<>();

    public List<FireStations> getListOAllFireStations(){ return listOfAllFireStations; }
    public List<MedicalRecords> getListOfAllMedicalRecords(){return listOfAllMedicalRecords;}

    public List<MedicalRecords> setListOfAllMedicalRecords(List<MedicalRecords> listOfAllMedicalRecords)
    {
        return this.listOfAllMedicalRecords = listOfAllMedicalRecords;
    }

    //method that reads the url and converts to json
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

    //method which get all person from the url and convert it in jsonArray
    @Override
    public JSONArray getPersonsJson() throws JSONException {

        JSONObject jsonObject = new JSONObject();

        try {
            String url = "https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json";
            jsonObject = readJsonFromUrl(url);


        }
        catch (Exception ex) {
            logger.error("Error fetching the Json list of Person", ex);
        }

        return jsonObject.getJSONArray("persons");
    }

    //method which convert the jsonArray of all persons in a list of model Persons
    @Override
    public List<Persons> getPersons(){

        if(!listOfAllPersons.isEmpty())
        {
            return listOfAllPersons;
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JSONArray jsonArray = getPersonsJson();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonPerson = jsonArray.getJSONObject(i);
                String personConvertToString = jsonPerson.toString();
                Persons personObject = objectMapper.readValue(personConvertToString, Persons.class);
                listOfAllPersons.add(personObject);

                List<MedicalRecords> listOfMedicalRecords = getMedicalRecords();

                for(MedicalRecords medicalRecord : listOfMedicalRecords)
                {
                    String medicalRecordFirstName = medicalRecord.getFirstName();
                    String medicalRecordLastName = medicalRecord.getLastName();

                    listOfAllPersons.stream().filter(p -> p.getFirstName().equals(medicalRecordFirstName) && p.getLastName().equals(medicalRecordLastName)).
                            forEach(p -> p.setMedicalRecords(medicalRecord));
                }
            }
        }
        catch (Exception ex) {
            logger.error("Error fetching the list of Person", ex);
        }
        return listOfAllPersons;
    }

    //
    @Override
    public List<Persons> getPersonByLastName(String lastName){

            listOfAllPersons = getPersons();


        List<Persons> listOfPersonsWithTheSameLastName = new ArrayList<>();
        try {
            for (Persons person : listOfAllPersons) {
                if (lastName.equals(person.getLastName())) {
                    listOfPersonsWithTheSameLastName.add(person);
                }
            }

        } catch (Exception ex) {
            logger.error("Error fetching the list of persons with same firstName", ex);
        }
        return listOfPersonsWithTheSameLastName;
    }
    @Override
    public void calculateAgeOfPersons(List<Persons> listOfPersons)
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

    }

    @Override
    public List<Persons> getAllPersonsByAddress(String address) throws JSONException, JsonProcessingException {

            listOfAllPersons = getPersons();


        List<Persons> listOfPersonByAddress = new ArrayList<>();

        try {
            for (Persons person : listOfAllPersons) {
                if (address.equals(person.getAddress())) {
                    listOfPersonByAddress.add(person);
                }
            }

        } catch (Exception ex) {
            logger.error("Error fetching the persons by address", ex);
        }
        return listOfPersonByAddress;
    }

    @Override
    public JSONArray getMedicalRecordsJson() throws JSONException {

        JSONObject jsonObject = new JSONObject();

        try {
            String url = "https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json";
            jsonObject = readJsonFromUrl(url);


        }
        catch(Exception ex){
            logger.error("Error fetching the list of JSON Medical Records",ex);
        }

        return jsonObject.getJSONArray("medicalrecords");
    }

    @Override
    public List<MedicalRecords> getMedicalRecords() {

        if(!getListOfAllMedicalRecords().isEmpty())
        {
            return getListOfAllMedicalRecords();
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            JSONArray jsonArray = getMedicalRecordsJson();

//        for(Object jsonMedicalRecord : jsonArray)
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonMedicalRecord = jsonArray.getJSONObject(i);
                String medicalRecordConvertToString = jsonMedicalRecord.toString();
                MedicalRecords medicalRecordObject = objectMapper.readValue(medicalRecordConvertToString, MedicalRecords.class);
                listOfAllMedicalRecords.add(medicalRecordObject);
            }
        }
        catch(Exception ex){
            logger.error("Error fetching the list of Medical Records",ex);
        }
        setListOfAllMedicalRecords(listOfAllMedicalRecords);
        return listOfAllMedicalRecords;
    }
    @Override
    public JSONArray getFireStationsJson() throws JSONException {

        JSONObject jsonObject = new JSONObject();

        try {
            String url = "https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json";
            jsonObject = readJsonFromUrl(url);

        }
        catch(Exception ex){
            logger.error("Error fetching the list of Fire Stations",ex);
        }

        return jsonObject.getJSONArray("firestations");
    }

    @Override
    public List<FireStations> getFireStations() throws JSONException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
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

        FireStations fireStationSearch = new FireStations();

            //create the list of all fire stations
            listOfAllFireStations = getFireStations();

        try {
            for (FireStations fireStation : listOfAllFireStations) {
                if (fireStation.getAddress().equals(address)) {
                    fireStationSearch = fireStation;
                }
            }
        } catch (Exception ex) {
            logger.error("Error fetching the Fire Station which cover the address", ex);
        }

        return fireStationSearch;
    }


    @Override
    public List<MedicalRecords> getMedicalRecordsByAddress(String address) {

            listOfAllMedicalRecords = getMedicalRecords();

        //crete the list of medical records of the address
        List<MedicalRecords> listOfMedicalRecordsByAddress = new ArrayList<>();

        try {
            //crete list of all persons which live in the address
            List<Persons> listOfPersonsWhoLiveInAddress = getAllPersonsByAddress(address);
            //crete list of all medical records


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
        }
        catch(Exception ex){
            logger.error("Error fetching the list of Medical Records by address",ex);
        }
        return listOfMedicalRecordsByAddress;
    }

    @Override
    public List<FireStations> getFireStationByStationNumber(int stationNumber) throws JSONException, JsonProcessingException {

        List<FireStations> listOfFireStationsByStationNumber = new ArrayList<>();


            listOfAllFireStations = getFireStations();

        try
        {
            for(FireStations fireStation : listOfAllFireStations)
            {
                int fireStationNumber = fireStation.getStation();

                if(fireStationNumber == stationNumber)
                {
                    listOfFireStationsByStationNumber.add(fireStation);
                }
            }
        }
        catch(Exception ex){
            logger.error("Error fetching the list of FireStations by station Number",ex);
        }
        return listOfFireStationsByStationNumber;
    }


    @Override
    public void saveNewPerson(Persons person)
    {

            listOfAllPersons = getPersons();

        try {
            listOfAllPersons.add(person);

            logger.debug("The new person was saved");
        }
        catch(Exception ex){
            logger.error("Error save the new person ",ex);
        }
    }

    @Override
    public void deletePerson(String firstName, String lastName)
    {
            listOfAllPersons = getPersons();

        List<Integer> listOfIndexToRemove = new ArrayList<>();

        try
        {
            for(Persons person : listOfAllPersons)
            {
                String firstNamePerson = person.getFirstName();
                String lastNamePerson = person.getLastName();
                if(firstNamePerson.equals(firstName) && lastNamePerson.equals(lastName))
                {
                    int indexOfPerson = listOfAllPersons.indexOf(person);

                    listOfIndexToRemove.add(indexOfPerson);
                }
            }
            for (int indexOfPerson : listOfIndexToRemove)
            {
                listOfAllPersons.remove(indexOfPerson);
                logger.debug("The person was delete");
            }
        }
        catch(Exception ex){
            logger.error("Error delete the person ",ex);
        }
    }

    @Override
    public void upDatePersonInfo(Persons person)
    {
            listOfAllPersons = getPersons();

        try
        {
            for (Persons personTarget : listOfAllPersons)
            {
                String firstNamePerson = personTarget.getFirstName();
                String lastNamePerson = personTarget.getLastName();

                if(firstNamePerson.equals(person.getFirstName()) && lastNamePerson.equals(person.getLastName()))
                {
                    personTarget.setAge(person.getAge());
                    personTarget.setMedicalRecords(person.getMedicalRecords());
                    personTarget.setCity(person.getCity());
                    personTarget.setEmail(person.getEmail());
                    personTarget.setAddress(person.getAddress());
                    personTarget.setPhone(person.getPhone());
                    personTarget.setZip(person.getZip());
                }
            }
            logger.debug("The person's info were update");
        }
        catch(Exception ex){
            logger.error("Error update the person's info ",ex);
        }
    }

    @Override
    public void saveNewFireStation(FireStations fireStation) throws JSONException, JsonProcessingException {

            listOfAllFireStations = getFireStations();

        try {
            listOfAllFireStations.add(fireStation);

            logger.debug("The new fire station was saved");
        }
        catch(Exception ex){
            logger.error("Error save the new fire station ",ex);
        }
    }

    @Override
    public void deleteFireStationByStationNumber(int stationNumber) throws JSONException, JsonProcessingException {

            listOfAllFireStations = getFireStations();

        List<Integer> listOfIndexToRemove = new ArrayList<>();
        try
        {
            for(FireStations fireStation : listOfAllFireStations)
            {
                int stationNumberOfSelectedFireStation = fireStation.getStation();

                if(stationNumberOfSelectedFireStation==stationNumber)
                {
                    int indexOfStation = listOfAllFireStations.indexOf(fireStation);
                    listOfIndexToRemove.add(indexOfStation);
                }
            }
            for(int index : listOfIndexToRemove)
            {
                listOfAllFireStations.remove(index);
                logger.debug("The fire station was delete");
            }
        }
        catch(Exception ex){
            logger.error("Error delete the fire station by its station number",ex);
        }
    }



    @Override
    public void deleteFireStationByAddress(String address) throws JSONException, JsonProcessingException {

            listOfAllFireStations = getFireStations();


        List<Integer> listOfIndexToRemove = new ArrayList<>();

        try
        {
            for(FireStations fireStation : listOfAllFireStations)
            {
                String addressOfFireStationSelected=fireStation.getAddress();

                if(address.equals(addressOfFireStationSelected))
                {
                    int indexOfStation = listOfAllFireStations.indexOf(fireStation);
                    listOfIndexToRemove.add(indexOfStation);
                }
            }
            for(int index : listOfIndexToRemove)
            {
                listOfAllFireStations.remove(index);
                logger.debug("The fire station was delete");
            }
        }
        catch(Exception ex){
            logger.error("Error delete the fire station ",ex);
        }
    }

    @Override
    public void upDateStationNumber(String address, int stationNumber) throws JSONException, JsonProcessingException {

            listOfAllFireStations = getFireStations();

        try
        {
            for(FireStations fireStation : listOfAllFireStations)
            {
                if(address.equals(fireStation.getAddress()))
                {
                    fireStation.setStation(stationNumber);

                    logger.debug("The station Number at the address :"+address+" was upDated");
                }
            }
        }
        catch(Exception ex){
            logger.error("Error update the fire station Number",ex);
        }
    }

    @Override
    public void saveNewMedicalRecords(MedicalRecords medicalRecord)
    {
            listOfAllMedicalRecords = getMedicalRecords();

        try
        {
            listOfAllMedicalRecords.add(medicalRecord);
            logger.debug("the new medical records were saved");
        }
        catch(Exception ex){
            logger.error("Error save the new medical records",ex);
        }
    }

    @Override
    public void upDateMedicalRecords(MedicalRecords medicalRecord)
    {
            listOfAllMedicalRecords = getMedicalRecords();

        try
        {
            for (MedicalRecords medicalRecordSelected : listOfAllMedicalRecords)
            {
                String firstNameSelectedMedicalRecord = medicalRecordSelected.getFirstName();
                String lastNameSelectedMedicalRecord = medicalRecordSelected.getLastName();

                if(firstNameSelectedMedicalRecord.equals(medicalRecord.getFirstName())&& lastNameSelectedMedicalRecord.equals(medicalRecord.getLastName()))
                {
                    medicalRecordSelected.setMedications(medicalRecord.getMedications());
                    medicalRecordSelected.setAllergies(medicalRecord.getAllergies());

                    logger.debug("the medical records were update");
                }
            }
        }
        catch(Exception ex){
            logger.error("Error update the medical records",ex);
        }
    }

    @Override
    public void deleteMedicalRecords(String firstName, String lastName)
    {

            listOfAllMedicalRecords = getMedicalRecords();

        try
        {
            for (MedicalRecords medicalRecordSelected : listOfAllMedicalRecords)
            {
                String firstNameSelectedMedicalRecord = medicalRecordSelected.getFirstName();
                String lastNameSelectedMedicalRecord = medicalRecordSelected.getLastName();

                if(firstNameSelectedMedicalRecord.equals(firstName) && lastNameSelectedMedicalRecord.equals(lastName))
                {
                    List<String> medicationToDelete = medicalRecordSelected.getMedications();
                    List<String> allergiesToDelete = medicalRecordSelected.getAllergies();

                    medicalRecordSelected.getMedications().removeAll(medicationToDelete);
                    medicalRecordSelected.getAllergies().removeAll(allergiesToDelete);

                    logger.debug("the medical records were delete");
                }
            }
        }
        catch(Exception ex){
            logger.error("Error delete the medical records",ex);
        }
    }

}
