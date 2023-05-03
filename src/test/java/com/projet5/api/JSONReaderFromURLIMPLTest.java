package com.projet5.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.projet5.api.model.FireStations;
import com.projet5.api.model.MedicalRecords;
import com.projet5.api.model.Persons;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JSONReaderFromURLIMPLTest {

    private static JSONReaderFromURLIMPL jsonReaderFromURLIMPL;

    //crete a list of persons with medicalRecords and a fireStation which cover them, for control the different method of the repository
    @BeforeEach
    public void setUp() throws JSONException, JsonProcessingException {
        jsonReaderFromURLIMPL = new JSONReaderFromURLIMPL();

        String addressSelected = "11 way of Yellowstone";

        Persons child = new Persons();
        Persons father = new Persons();

        MedicalRecords medicalRecordsOfChild = new MedicalRecords();
        medicalRecordsOfChild.setBirthdate("20/12/2020");
        medicalRecordsOfChild.setFirstName("Beth");
        medicalRecordsOfChild.setLastName("Dutton");

        MedicalRecords medicalRecordsOfFather = new MedicalRecords();
        medicalRecordsOfFather.setBirthdate("20/12/1975");
        medicalRecordsOfFather.setLastName("Dutton");
        medicalRecordsOfFather.setFirstName("John");
        List<String> allergiesFather = new ArrayList<>();
        allergiesFather.add("nillacilan");
        List<String> medicationsFather = new ArrayList<>();
        medicalRecordsOfFather.setAllergies(allergiesFather);
        medicalRecordsOfFather.setMedications(medicationsFather);

        child.setAddress(addressSelected);
        child.setFirstName("Beth");
        child.setLastName("Dutton");
        child.setMedicalRecords(medicalRecordsOfChild);
        child.setCity("MontanaCity");

        father.setAddress(addressSelected);
        father.setFirstName("John");
        father.setLastName("Dutton");
        father.setMedicalRecords(medicalRecordsOfFather);
        father.setCity("MontanaCity");

        jsonReaderFromURLIMPL.saveNewPerson(child);
        jsonReaderFromURLIMPL.saveNewPerson(father);
        jsonReaderFromURLIMPL.saveNewMedicalRecords(medicalRecordsOfChild);
        jsonReaderFromURLIMPL.saveNewMedicalRecords(medicalRecordsOfFather);

        FireStations fireStationSearch = new FireStations();
        fireStationSearch.setStation(777);
        fireStationSearch.setAddress("22 yellowstone street");

        jsonReaderFromURLIMPL.saveNewFireStation(fireStationSearch);
    }

    //verify if the method readURL works and return a jsonObject
    @Test
    public void testToReadUrl() throws JSONException, IOException
    {
        String url = "https://s3-eu-west-1.amazonaws.com/course.oc-static.com/projects/DA+Java+EN/P5+/data.json";
        JSONObject jsonObject = jsonReaderFromURLIMPL.readJsonFromUrl(url);

        assertNotNull(jsonObject);
    }

    //verify of the method getPersons(), return a list of persons from the url
    @Test
    public void testToGetPerson()
    {
        List<Persons> listOfAllPersons = new ArrayList<>();

        listOfAllPersons = jsonReaderFromURLIMPL.getPersons();

        assertNotNull(listOfAllPersons);
        assertEquals(listOfAllPersons.get(0).getClass(), Persons.class);
    }

    //verify if we can get a person by his lastName
    @Test
    public void testGetPersonByLastName()
    {
        List<Persons> recoveredPerson =  jsonReaderFromURLIMPL.getPersonByLastName("Dutton");

        assertEquals(recoveredPerson.get(0).getFirstName(), "Beth");
        assertEquals(recoveredPerson.get(0).getAddress(), "11 way of Yellowstone");
    }

    //test to verify if the method calculate the age of person works correctly
    @Test
    public void testToCalculateAgeOfPersons() throws ParseException
    {
        List<Persons> listOfPersonsToTest = new ArrayList<>();

        Persons child = new Persons();
        Persons father = new Persons();

        MedicalRecords medicalRecordsOfChild = new MedicalRecords();
        medicalRecordsOfChild.setBirthdate("20/12/2020");


        MedicalRecords medicalRecordsOfFather = new MedicalRecords();
        medicalRecordsOfFather.setBirthdate("20/12/1975");



        child.setFirstName("Beth");
        child.setLastName("Dutton");
        child.setMedicalRecords(medicalRecordsOfChild);

        father.setFirstName("John");
        father.setLastName("Dutton");
        father.setMedicalRecords(medicalRecordsOfFather);

        listOfPersonsToTest.add(child);
        listOfPersonsToTest.add(father);

        Date nowDate = new Date();
        String birthdateStringChild = child.getMedicalRecords().getBirthdate();
        String birthdateStringFather= father.getMedicalRecords().getBirthdate();

        Date birthdateDateChild = new SimpleDateFormat("dd/MM/yyyy").parse(birthdateStringChild);
        Date birthdateDateFather = new SimpleDateFormat("dd/MM/yyyy").parse(birthdateStringFather);

        Double ageInDoubleChildExpected = (nowDate.getTime()-birthdateDateChild.getTime())/3.154e+10;
        Double ageInDoubleFatherExpected = (nowDate.getTime()-birthdateDateFather.getTime())/3.154e+10;

        jsonReaderFromURLIMPL.calculateAgeOfPersons(listOfPersonsToTest);


        assertEquals(child.getAge(), ageInDoubleChildExpected.intValue());
        assertEquals(father.getAge(), ageInDoubleFatherExpected.intValue());

    }

    //try to get all persons by address
    @Test
    public void testToGetAllPersonsByAddress() throws JSONException, JsonProcessingException
    {
        String address = "11 way of Yellowstone";

        List<Persons> listOfAllPersonWhoLiveAtTheFakeAddress = jsonReaderFromURLIMPL.getAllPersonsByAddress(address);

        assertEquals(listOfAllPersonWhoLiveAtTheFakeAddress.get(0).getAddress(), address);
        assertEquals(listOfAllPersonWhoLiveAtTheFakeAddress.get(1).getAddress(), address);
    }

    //test to recover all fire stations
    @Test
    public void testToGetFireStation() throws JSONException, JsonProcessingException
    {
        List<FireStations> listOfAllFireStations = jsonReaderFromURLIMPL.getFireStations();

        assertNotNull(listOfAllFireStations);
    }

    //test to get a fire station by its address
    @Test
    public void testGetFireStationByAddress() throws JSONException, JsonProcessingException
    {
        String address = "22 yellowstone street";

        FireStations fireStation = jsonReaderFromURLIMPL.getFireStationByAddress(address);

        assertEquals(fireStation.getStation(), 777);
    }

    //test to recover all medicalRecords
    @Test
    public void testGetMedicalRecords()
    {
        List<MedicalRecords> listOfMedicalRecordsExpected = jsonReaderFromURLIMPL.getMedicalRecords();

        assertNotNull(listOfMedicalRecordsExpected);
    }


    //test to recover all medicalRecords by address
    @Test
    public void testGetMedicalRecordsByAddress()
    {
        String addressSelected = "11 way of Yellowstone";


        List<MedicalRecords> listOfMedicalRecordsByAddress = jsonReaderFromURLIMPL.getMedicalRecordsByAddress(addressSelected);

        assertEquals(listOfMedicalRecordsByAddress.size(), 2);
        assertEquals(listOfMedicalRecordsByAddress.get(0).getBirthdate(), "20/12/2020");
    }

    //test to recover the firestation by address
    @Test
    public void testToGetFireStationByStationNumber() throws JSONException, JsonProcessingException
    {
        List<FireStations> ListOfFireStationSearch = jsonReaderFromURLIMPL.getFireStationByStationNumber(777);

        assertEquals(ListOfFireStationSearch.size(), 1);
        assertEquals(ListOfFireStationSearch.get(0).getAddress(), "22 yellowstone street");
    }

    //test to save a new person
    @Test
    public void testToSaveANewPerson()
    {
        Persons personToSave = new Persons();
        personToSave.setFirstName("Bob");
        personToSave.setLastName("Sponge");

        jsonReaderFromURLIMPL.saveNewPerson(personToSave);

        List<Persons> personSearch = jsonReaderFromURLIMPL.getPersonByLastName("Sponge");

        assertEquals(personSearch.get(0).getFirstName(), personToSave.getFirstName());
    }

    //test to delete a person
    @Test
    public void testToDeleteAPerson()
    {
        jsonReaderFromURLIMPL.deletePerson("Beth", "Dutton");

        List<Persons> listOfPersonSearch = jsonReaderFromURLIMPL.getPersonByLastName("Dutton");

        assertEquals(listOfPersonSearch.size(), 1);
    }

    //test to update the info of a person
    @Test
    public void testToUpDatePersonInfo()
    {
        List<Persons> listOfPersonToUpDate = jsonReaderFromURLIMPL.getPersonByLastName("Dutton");
        Persons personToUpDate = listOfPersonToUpDate.get(0);
        personToUpDate.setCity("Smithfield");

        jsonReaderFromURLIMPL.upDatePersonInfo(personToUpDate);

        List<Persons> listOfPersonDutton = jsonReaderFromURLIMPL.getPersonByLastName("Dutton");
        Persons personBeth = listOfPersonDutton.get(0);
        assertEquals(personBeth.getCity(), "Smithfield");
    }

    //test to save a new fireStation
    @Test
    public void testToSaveNewFireStation() throws JSONException, JsonProcessingException
    {
        FireStations fireStationToSave = new FireStations();
        fireStationToSave.setAddress("10 comté street");
        fireStationToSave.setStation(999);

        jsonReaderFromURLIMPL.saveNewFireStation(fireStationToSave);
        FireStations fireStations = jsonReaderFromURLIMPL.getFireStationByAddress("10 comté street");

        assertEquals(fireStations.getStation(), 999);
    }

    //test to delete a fireStation by its address
    @Test
    public void testToDeleteFireStationByAddress() throws JSONException, JsonProcessingException
    {
        jsonReaderFromURLIMPL.deleteFireStationByAddress("22 yellowstone street");

        FireStations fireStationByAddress = jsonReaderFromURLIMPL.getFireStationByAddress("22 yellowstone street");

        assertEquals(fireStationByAddress.getStation(), 0);
        assertNull(fireStationByAddress.getAddress());
    }

    //test to delete a fireStation by its station number
    @Test
    public void testToDeleteAFireStationByItsNumber() throws JSONException, JsonProcessingException
    {
        jsonReaderFromURLIMPL.deleteFireStationByStationNumber(777);

        List<FireStations> listOfFireStationByNumber = jsonReaderFromURLIMPL.getFireStationByStationNumber(777);

        assertEquals(listOfFireStationByNumber.size(), 0);
    }

    //test to update the station number of a fireStation
    @Test
    public void testToUpDateStationNumberOfFireStation() throws JSONException, JsonProcessingException
    {
        jsonReaderFromURLIMPL.upDateStationNumber("22 yellowstone street", 778);

        FireStations fireStation = jsonReaderFromURLIMPL.getFireStationByAddress("22 yellowstone street");

        assertEquals(fireStation.getStation(), 778);
    }

    //test to save new medical records
    @Test
    public void testToSaveNewMedicalRecords()
    {
        int sizeOfAllMedicalRecordsBeforeSaveTheNew = jsonReaderFromURLIMPL.getListOfAllMedicalRecords().size();

        MedicalRecords medicalRecordsToSave = new MedicalRecords();
        medicalRecordsToSave.setFirstName("Bob");
        medicalRecordsToSave.setLastName("Marley");
        List<String>medicationsToSave = new ArrayList<>();
        medicationsToSave.add("doliprane : 100mg");
        medicalRecordsToSave.setMedications(medicationsToSave);

        jsonReaderFromURLIMPL.saveNewMedicalRecords(medicalRecordsToSave);

        int sizeOfAllMedicalRecordsAfterSaveTheNew = jsonReaderFromURLIMPL.getListOfAllMedicalRecords().size();

        assertEquals(sizeOfAllMedicalRecordsAfterSaveTheNew, sizeOfAllMedicalRecordsBeforeSaveTheNew+1);
    }

    //test to upDate medical records
    @Test
    public void testToUpDateMedicalRecords() {
        MedicalRecords medicalRecordsToUpDate = new MedicalRecords();
        medicalRecordsToUpDate.setFirstName("Beth");
        medicalRecordsToUpDate.setLastName("Dutton");
        List<String> listOfAllergiesToUpDate = new ArrayList<>();
        listOfAllergiesToUpDate.add("peanuts");
        listOfAllergiesToUpDate.add("apple");
        medicalRecordsToUpDate.setAllergies(listOfAllergiesToUpDate);

        jsonReaderFromURLIMPL.upDateMedicalRecords(medicalRecordsToUpDate);


        int allergiesNumber = jsonReaderFromURLIMPL.getMedicalRecordsByAddress("11 way of Yellowstone").get(0).getAllergies().size();
        String allergiesTarget = jsonReaderFromURLIMPL.getMedicalRecordsByAddress("11 way of Yellowstone").get(0).getAllergies().get(0);

        assertEquals(allergiesNumber, 2);
        assertEquals(allergiesTarget, "peanuts");
    }

    //test to delete medical records
    @Test
    public void testToDeleteMedicalRecords()
    {
        jsonReaderFromURLIMPL.deleteMedicalRecords("John", "Dutton");

        int numberOfAllergiesForMrDuttonAfterDeletion = jsonReaderFromURLIMPL.getMedicalRecordsByAddress("11 way of Yellowstone").get(1).getAllergies().size();

        assertEquals(numberOfAllergiesForMrDuttonAfterDeletion, 0);
    }
}
