package com.projet5.api.integration;

import com.projet5.api.model.MedicalRecords;
import com.projet5.api.model.Persons;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import com.projet5.api.service.MedicalRecordsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MedicalRecordsServiceIT {

    public List<MedicalRecords> getListOfMedicalRecords() {
        return listOfMedicalRecords;
    }

    private List<MedicalRecords> listOfMedicalRecords = new ArrayList<>();
    private JSONReaderFromURLIMPL jsonReaderFromURLIMPL = new JSONReaderFromURLIMPL() {

        @Override
        public List<MedicalRecords> getMedicalRecords()
        {
            return getListOfMedicalRecords();
        }
    };

    private MedicalRecordsService medicalRecordsService;

    //create a person and her medicalRecords to test the different method of MedicalRecordsService
    @BeforeEach
    public void setUp()
    {
        medicalRecordsService = new MedicalRecordsService();

        medicalRecordsService.setJsonReaderFromURLIMPL(jsonReaderFromURLIMPL);

        MedicalRecords medicalRecords = new MedicalRecords();
        medicalRecords.setFirstName("Beth");
        medicalRecords.setLastName("Dutton");
        List<String> medications = new ArrayList<>();
        medications.add("Allopurinol : 10mg");
        medicalRecords.setMedications(medications);

        List<String> allergies = new ArrayList<>();
        medicalRecords.setAllergies(allergies);

        Persons personBeth = new Persons("Beth", "Dutton", "11 yellowstone way","Montana city",0, null, null, null, medicalRecords, 40);

        jsonReaderFromURLIMPL.saveNewPerson(personBeth);

        getListOfMedicalRecords().add(medicalRecords);
    }

    //test to add a new medical records
    @Test
    public void testToAddNewMedicalRecords()
    {

        MedicalRecords medicalRecordsToAdd = new MedicalRecords();
        medicalRecordsToAdd.setFirstName("Beth");
        medicalRecordsToAdd.setLastName("Dutton");
        List<String> medications = new ArrayList<>();
        medications.add("Allopurinol : 10mg");
        List<String> allergies = new ArrayList<>();
        allergies.add("peanut");
        medicalRecordsToAdd.setMedications(medications);
        medicalRecordsToAdd.setAllergies(allergies);

        medicalRecordsService.saveNewMedicalRecords(medicalRecordsToAdd, "Beth", "Dutton");


        assertEquals(allergies.get(0), getListOfMedicalRecords().get(0).getAllergies().get(0));
    }

    //test to delete a medical records
    @Test
    public void testToDeleteAMedicalRecords()
    {
        medicalRecordsService.deleteMedicalRecords("Beth", "Dutton");

        assertEquals(getListOfMedicalRecords().get(0).getFirstName(), "Beth");
        assertEquals(getListOfMedicalRecords().get(0).getMedications().size(), 0);
        assertEquals(getListOfMedicalRecords().get(0).getAllergies().size(), 0);
    }

    //test to upDate the medicalRecords
    @Test
    public void testToUpDateTheMedicalRecordsOfAPerson()
    {
        MedicalRecords medicalRecordsToUpDate = new MedicalRecords();
        medicalRecordsToUpDate.setLastName("Dutton");
        medicalRecordsToUpDate.setFirstName("Beth");
        List<String>allergies = new ArrayList<>();
        allergies.add("Lemon");
        allergies.add("peanut");
        medicalRecordsToUpDate.setAllergies(allergies);

        medicalRecordsService.upDateMedicalRecords(medicalRecordsToUpDate);

        assertEquals(getListOfMedicalRecords().get(0).getAllergies().get(0), "Lemon");
    }
}
