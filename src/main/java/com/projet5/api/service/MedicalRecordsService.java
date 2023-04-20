package com.projet5.api.service;

import com.projet5.api.model.MedicalRecords;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Data
@Service
public class MedicalRecordsService {

    @Autowired
    JSONReaderFromURLIMPL jsonReaderFromURLIMPL;

    private static final Logger logger = LogManager.getLogger("Medical Records service");

    public void saveNewMedicalRecords(MedicalRecords medicalRecord)
    {
        jsonReaderFromURLIMPL.saveNewMedicalRecords(medicalRecord);
    }

    public void upDateMedicalRecords(MedicalRecords medicalRecord)
    {
        jsonReaderFromURLIMPL.upDateMedicalRecords(medicalRecord);
    }

    public void deleteMedicalRecords(String firstName, String lastName)
    {
        jsonReaderFromURLIMPL.deleteMedicalRecords(firstName, lastName);
    }



    //Pour tester
    public List<MedicalRecords> getAllMedicalRecords()
    {
      return  jsonReaderFromURLIMPL.getListOfAllMedicalRecords();
    }

}
