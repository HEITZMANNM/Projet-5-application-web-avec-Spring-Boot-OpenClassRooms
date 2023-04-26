package com.projet5.api;

import com.projet5.api.model.MedicalRecords;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import com.projet5.api.service.MedicalRecordsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordsServiceTest
{

    @Mock(lenient = true)
    private  JSONReaderFromURLIMPL jsonReaderFromURLIMPL;

    private  MedicalRecordsService medicalRecordsService;

    private  List<MedicalRecords> listOfAllMedicalRecords;

    @BeforeEach
    public void setUp()
    {


        listOfAllMedicalRecords = new ArrayList<>();
        MedicalRecords medicalRecords = new MedicalRecords();
        medicalRecords.setLastName("Dutton");
        medicalRecords.setFirstName("Beth");
        List<String> allergies = new ArrayList<>();
        allergies.add("peanut");
        medicalRecords.setAllergies(allergies);
        listOfAllMedicalRecords.add(medicalRecords);

        when(jsonReaderFromURLIMPL.getMedicalRecords()).thenReturn(listOfAllMedicalRecords);

        medicalRecordsService = new MedicalRecordsService();

        medicalRecordsService.setJsonReaderFromURLIMPL(jsonReaderFromURLIMPL);

    }

    //test to get all medicalRecords
    @Test
    public void testToGetAllMedicalRecords()
    {
      List<MedicalRecords> listOfAllMedicalRecords = medicalRecordsService.getAllMedicalRecords();

        assertEquals(listOfAllMedicalRecords.get(0).getFirstName(), "Beth");
    }

}
