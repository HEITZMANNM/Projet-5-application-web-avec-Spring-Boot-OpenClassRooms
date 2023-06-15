package com.projet5.api;

import com.projet5.api.model.MedicalRecords;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MedicalRecardsModelTest {
    @Test
    public void testHashCode()
    {
        MedicalRecords medicalRecords = new MedicalRecords();
        medicalRecords.setLastName("Dutton");
        medicalRecords.setLastName("John");
        List<String> medications = new ArrayList<>();
        medications.add("100g Albumine");
        List<String> alergies = new ArrayList<>();
        alergies.add("peanut");
        medicalRecords.setMedications(medications);
        medicalRecords.setAllergies(alergies);

        assertNotNull(medicalRecords.hashCode());

    }

    @Test
    public void testEquals()
    {
        MedicalRecords medicalRecords = new MedicalRecords();
        medicalRecords.setLastName("Dutton");
        medicalRecords.setLastName("John");
        List<String> medications = new ArrayList<>();
        medications.add("100g Albumine");
        List<String> alergies = new ArrayList<>();
        alergies.add("peanut");
        medicalRecords.setMedications(medications);
        medicalRecords.setAllergies(alergies);

        MedicalRecords medicalRecords2 = new MedicalRecords();
        medicalRecords2.setLastName("Dutton");
        medicalRecords2.setLastName("John");
        List<String> medications2 = new ArrayList<>();
        medications2.add("100g Albumine");
        List<String> alergies2 = new ArrayList<>();
        alergies2.add("peanut");
        medicalRecords2.setMedications(medications2);
        medicalRecords2.setAllergies(alergies2);

        MedicalRecords medicalRecords3 = new MedicalRecords();
        medicalRecords3.setLastName("Dutton");
        medicalRecords3.setLastName("John");
        List<String> medications3 = new ArrayList<>();
        medications3.add("200g Albumine");
        List<String> alergies3 = new ArrayList<>();
        alergies3.add("peanut");
        medicalRecords3.setMedications(medications3);
        medicalRecords3.setAllergies(alergies3);


        assertTrue(medicalRecords.equals(medicalRecords));

        assertTrue(medicalRecords.equals(medicalRecords2));

        medications2 = null;

        assertFalse(medicalRecords.equals(medications2));

        assertFalse((new MedicalRecords().equals(medicalRecords)));

        assertFalse(medicalRecords.equals(medications3));

        List<String> medications3UpDated = new ArrayList<>();
        medications3UpDated.add("100g Albumine");
        medicalRecords3.setMedications(medications3UpDated);

        medicalRecords3.setFirstName("Beth");

        assertFalse(medicalRecords.equals(medicalRecords3));
    }

    @Test
    public void testToString()
    {
        MedicalRecords medicalRecords3 = new MedicalRecords();
        medicalRecords3.setLastName("Dutton");
        medicalRecords3.setLastName("John");
        List<String> medications3 = new ArrayList<>();
        medications3.add("200g Albumine");
        List<String> alergies3 = new ArrayList<>();
        alergies3.add("peanut");
        medicalRecords3.setMedications(medications3);
        medicalRecords3.setAllergies(alergies3);

        assertNotNull(medicalRecords3.toString());
    }
}
