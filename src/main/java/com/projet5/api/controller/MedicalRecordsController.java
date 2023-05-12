package com.projet5.api.controller;


import com.projet5.api.model.MedicalRecords;
import com.projet5.api.service.MedicalRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MedicalRecordsController {

    @Autowired
    MedicalRecordsService medicalRecordsService;

    @GetMapping("/medicalRecord")
    public List<MedicalRecords> getAllMedicalRecords()
    {
       return medicalRecordsService.getAllMedicalRecords();
    }

    @PostMapping("/medicalRecord")
    public ResponseEntity<HttpStatus> saveNewMedicalRecords(@RequestBody MedicalRecords medicalRecord, @RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName)
    {
        if(medicalRecordsService.saveNewMedicalRecords(medicalRecord, firstName, lastName))
        {
            return new ResponseEntity<HttpStatus>(HttpStatus.CREATED);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<HttpStatus> upDateMedicalRecords(@RequestBody MedicalRecords medicalRecord)
    {
        if(medicalRecordsService.upDateMedicalRecords(medicalRecord))
        {
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        }
       return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<HttpStatus>  deleteMedicalRecords(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName)
    {
        if(medicalRecordsService.deleteMedicalRecords(firstName, lastName))
        {
            return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<HttpStatus>(HttpStatus.BAD_REQUEST);
    }

    public void setMedicalRecordsService(MedicalRecordsService medicalRecordsService) {
        this.medicalRecordsService = medicalRecordsService;
    }
}

