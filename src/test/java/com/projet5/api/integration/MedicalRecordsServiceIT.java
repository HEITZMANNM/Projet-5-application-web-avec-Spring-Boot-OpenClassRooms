package com.projet5.api.integration;

import com.projet5.api.model.MedicalRecords;
import com.projet5.api.model.Persons;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import com.projet5.api.service.MedicalRecordsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MedicalRecordsServiceIT {

    private static JSONReaderFromURLIMPL jsonReaderFromURLIMPL;

    private static MedicalRecordsService medicalRecordsService;

    @BeforeAll
    public static void setUp()
    {
        medicalRecordsService = new MedicalRecordsService();
        jsonReaderFromURLIMPL = new JSONReaderFromURLIMPL();

        medicalRecordsService.setJsonReaderFromURLIMPL(jsonReaderFromURLIMPL);

        MedicalRecords medicalRecords = new MedicalRecords();
        medicalRecords.setFirstName("Beth");
        medicalRecords.setLastName("Dutton");
        List<String> medications = new ArrayList<>();
        medications.add("Allopurinol : 10mg");
        List<String> allergies = new ArrayList<>();
        allergies.add("peanut");
        medicalRecords.setMedications(medications);
        medicalRecords.setAllergies(allergies);

        medicalRecordsService.saveNewMedicalRecords(medicalRecords);

        Persons personBeth = new Persons("Beth", "Dutton", "11 yellowstone way","Montana city",0, null, null, null, medicalRecords, 40);

        jsonReaderFromURLIMPL.saveNewPerson(personBeth);


    }

    //test to add a new medical records
    @Test
    public void testToAddNewMedicalRecords()
    {
        int sizeOfAllMedicalRecordsBeforeSaveTheNew = jsonReaderFromURLIMPL.getMedicalRecords().size();

        MedicalRecords medicalRecordsToAdd = new MedicalRecords();
        medicalRecordsToAdd.setFirstName("John");
        medicalRecordsToAdd.setLastName("Dutton");
        List<String> medications = new ArrayList<>();
        medications.add("Allopurinol : 10mg");
        List<String> allergies = new ArrayList<>();
        allergies.add("peanut");
        medicalRecordsToAdd.setMedications(medications);
        medicalRecordsToAdd.setAllergies(allergies);

        medicalRecordsService.saveNewMedicalRecords(medicalRecordsToAdd);

        int sizeOfAllMedicalRecordsAfterSaveTheNew = jsonReaderFromURLIMPL.getListOfAllMedicalRecords().size();

        assertEquals(sizeOfAllMedicalRecordsAfterSaveTheNew, sizeOfAllMedicalRecordsBeforeSaveTheNew+1);
    }

    //test to delete a medical records
    @Test
    public void testToDeleteAMedicalRecords()
    {

        medicalRecordsService.deleteMedicalRecords("Beth", "Dutton");

        List<MedicalRecords> medicalRecordsSearch = jsonReaderFromURLIMPL.getMedicalRecordsByAddress("11 yellowstone way");


        assertEquals(medicalRecordsSearch.get(0).getFirstName(), "Beth");
        assertEquals(medicalRecordsSearch.get(0).getMedications().size(), 0);
        assertEquals(medicalRecordsSearch.get(0).getAllergies().size(), 0);
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

        List<MedicalRecords> medicalRecordsSearch = jsonReaderFromURLIMPL.getMedicalRecordsByAddress("11 yellowstone way");

        assertEquals(medicalRecordsSearch.get(0).getAllergies().get(0), "Lemon");
    }
}
