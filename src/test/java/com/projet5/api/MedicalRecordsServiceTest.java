package com.projet5.api;

import com.projet5.api.model.MedicalRecords;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import com.projet5.api.service.MedicalRecordsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordsServiceTest
{

    @Mock(lenient = true)
    private  JSONReaderFromURLIMPL jsonReaderFromURLIMPL;

    @InjectMocks
    private  MedicalRecordsService medicalRecordsService = new MedicalRecordsService();

    private  List<MedicalRecords> listOfAllMedicalRecords;

    //create a list of medicalRecords, which will be used to control the different methods of MedicalRecordsService
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
    }

    //test to get all medicalRecords
    @Test
    public void testToGetAllMedicalRecords()
    {
      List<MedicalRecords> listOfAllMedicalRecords = medicalRecordsService.getAllMedicalRecords();

        assertEquals(listOfAllMedicalRecords.get(0).getFirstName(), "Beth");
    }

}
