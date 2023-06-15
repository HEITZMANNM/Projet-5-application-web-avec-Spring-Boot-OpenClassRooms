package com.projet5.api.service;

import com.projet5.api.model.MedicalRecords;
import com.projet5.api.repository.IRepository;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MedicalRecordsServiceImpl implements MedicalRecordsService {

    @Autowired
    JSONReaderFromURLIMPL jsonReaderFromURLIMPL;

    //setter used in controller test and IT test
    public void setJsonReaderFromURLIMPL(IRepository repository)
    {
        this.jsonReaderFromURLIMPL= (JSONReaderFromURLIMPL) repository;
    }

    public JSONReaderFromURLIMPL getJsonReaderFromURLIMPL()
    {
        return jsonReaderFromURLIMPL;
    }

    private static final Logger logger = LogManager.getLogger("Medical Records service");

    public List<MedicalRecords> getAllMedicalRecords()
    {
       return jsonReaderFromURLIMPL.getMedicalRecords();
    }
    public boolean saveNewMedicalRecords(MedicalRecords medicalRecord, String firstName, String lastName)
    {
        return jsonReaderFromURLIMPL.saveNewMedicalRecords(medicalRecord, firstName, lastName);
    }

    public boolean upDateMedicalRecords(MedicalRecords medicalRecord)
    {
        return jsonReaderFromURLIMPL.upDateMedicalRecords(medicalRecord);
    }

    public boolean deleteMedicalRecords(String firstName, String lastName)
    {
        return jsonReaderFromURLIMPL.deleteMedicalRecords(firstName, lastName);
    }
}
