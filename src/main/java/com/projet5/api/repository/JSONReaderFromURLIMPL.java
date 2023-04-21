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

//    public List<Persons> setPersonsMedicalRecords(List<Persons> listOfPersons)
//    {
//        try{
//            List<MedicalRecords> listOfMedicalRecords = getMedicalRecords();
//
//            for(MedicalRecords medicalRecord : listOfMedicalRecords)
//            {
//                String medicalRecordFirstName = medicalRecord.getFirstName();
//                String medicalRecordLastName = medicalRecord.getLastName();
//
//                listOfPersons.stream().filter(p -> p.getFirstName().equals(medicalRecordFirstName) && p.getLastName().equals(medicalRecordLastName)).
//                        forEach(p -> p.setMedicalRecords(medicalRecord));
//            }
//return listOfPersons;
//        }
//        catch (Exception ex)
//        {
//            logger.error("Error fetching the list of Person with their medical records", ex);
//        }
//        return null;
//    }

    //    @Override
//    public List<Persons> calculateAgeOfPersons(List<Persons> listOfPersons)
//    {
//        try
//        {
//for (Persons person : listOfPersons)
//{
//    Date nowDate = new Date();
//    String birthdateString = person.getMedicalRecords().getBirthdate();
//    Date birthdateDate = new SimpleDateFormat("dd/MM/yyyy").parse(birthdateString);
//
//    Double ageInDouble = (nowDate.getTime()-birthdateDate.getTime())/3.154e+10;
//    int age =  ageInDouble.intValue();
//
//    person.setAge(age);
//}
//        }
//        catch (Exception ex)
//        {
//            logger.error("Error calculation for the age of each persons", ex);
//        }
//        return null;
//    }
    @Override
    public List<Persons> getPersonByLastName(String lastName){
        if (listOfAllPersons.isEmpty())
        {
            listOfAllPersons = getPersons();
        }

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
        if(listOfAllPersons.isEmpty())
        {
            listOfAllPersons = getPersons();
        }

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

        if(listOfAllFireStations.isEmpty())
        {
            //create the list of all fire stations
            listOfAllFireStations = getFireStations();
        }
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

        //crete the list of medical records of the address
        List<MedicalRecords> listOfMedicalRecordsByAddress = new ArrayList<>();

        try {
            //crete list of all persons which live in the address
            List<Persons> listOfPersonsWhoLiveInAddress = getAllPersonsByAddress(address);
            //crete list of all medical records
            List<MedicalRecords> listOfAllMedicalRecords = getMedicalRecords();


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

        if(listOfAllFireStations.isEmpty())
        {
            listOfAllFireStations = getFireStations();
        }
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


    @Override
    public void saveNewPerson(Persons person)
    {
        if(listOfAllPersons.isEmpty())
        {
            listOfAllPersons = getPersons();
        }
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
        if(listOfAllPersons.isEmpty())
        {
            listOfAllPersons = getPersons();
        }
        try
        {
            for(Persons person : listOfAllPersons)
            {
                String firstNamePerson = person.getFirstName();
                String lastNamePerson = person.getLastName();
                if(firstNamePerson.equals(firstName) && lastNamePerson.equals(lastName))
                {
                    int indexOfPerson = listOfAllPersons.indexOf(person);
                    listOfAllPersons.remove(indexOfPerson);
                    logger.debug("The person was delete");
                }
            }
        }
        catch(Exception ex){
            logger.error("Error delete the person ",ex);
        }
    }

    @Override
    public void upDatePersonInfo(Persons person)
    {
        if(listOfAllPersons.isEmpty())
        {
            listOfAllPersons = getPersons();
        }
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
        if(listOfAllFireStations.isEmpty())
        {
            listOfAllFireStations = getFireStations();
        }
        try {
            listOfAllFireStations.add(fireStation);

            logger.debug("The new fire station was saved");
        }
        catch(Exception ex){
            logger.error("Error save the new fire station ",ex);
        }
    }

    @Override
    public void deleteFireStation(int stationNumber, String address) throws JSONException, JsonProcessingException {
        if (listOfAllFireStations.isEmpty())
        {
            listOfAllFireStations = getFireStations();
        }
        try
        {
            for(FireStations fireStations : listOfAllFireStations)
            {
                String addressTarget=fireStations.getAddress();
                int stationNumberTarget=fireStations.getStation();

                if(!address.isEmpty())
                {
                    if(address.equals(addressTarget))
                    {
                        int indexOfStation = listOfAllFireStations.indexOf(fireStations);
                        listOfAllPersons.remove(indexOfStation);
                        logger.debug("The fire station was delete");
                    }
                }
                else
                {
                    if(stationNumber==stationNumberTarget)
                    {
                        int indexOfStation = listOfAllFireStations.indexOf(fireStations);
                        listOfAllPersons.remove(indexOfStation);
                        logger.debug("The fire station was delete");
                    }
                }
            }

        }
        catch(Exception ex){
            logger.error("Error delete the fire station ",ex);
        }
    }

    @Override
    public void upDateStationNumber(String address, int stationNumber) throws JSONException, JsonProcessingException {
        if (listOfAllFireStations.isEmpty())
        {
            listOfAllFireStations = getFireStations();
        }
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
        if (listOfAllMedicalRecords.isEmpty())
        {
            listOfAllMedicalRecords = getMedicalRecords();
        }
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
        if (listOfAllMedicalRecords.isEmpty())
        {
            listOfAllMedicalRecords = getMedicalRecords();
        }
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
        if (listOfAllMedicalRecords.isEmpty())
        {
            listOfAllMedicalRecords = getMedicalRecords();
        }
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
