package com.projet5.api.service;

import com.projet5.api.model.MedicalRecords;
import com.projet5.api.repository.IRepository;
import com.projet5.api.repository.JSONReaderFromURLIMPL;

import java.util.List;

public interface MedicalRecordsService {

    public void setJsonReaderFromURLIMPL(IRepository repository);

    public JSONReaderFromURLIMPL getJsonReaderFromURLIMPL();

    public List<MedicalRecords> getAllMedicalRecords();

    public boolean saveNewMedicalRecords(MedicalRecords medicalRecord, String firstName, String lastName);

    public boolean upDateMedicalRecords(MedicalRecords medicalRecord);

    public boolean deleteMedicalRecords(String firstName, String lastName);
}
