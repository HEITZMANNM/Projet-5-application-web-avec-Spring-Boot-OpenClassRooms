package com.projet5.api.controller;


import com.projet5.api.model.MedicalRecords;
import com.projet5.api.service.MedicalRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
    public ResponseEntity<Void> saveNewMedicalRecords(@RequestBody MedicalRecords medicalRecord)
    {
        medicalRecordsService.saveNewMedicalRecords(medicalRecord);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/medicalRecord")
                .buildAndExpand(medicalRecord.getLastName())
                .toUri();
        return ResponseEntity.created(location).build();


    }

    @PutMapping("/medicalRecord")
    public MedicalRecords upDateMedicalRecords(@RequestBody MedicalRecords medicalRecord)
    {
        medicalRecordsService.upDateMedicalRecords(medicalRecord);

        return medicalRecord;
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<HttpStatus>  deleteMedicalRecords(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName)
    {
        medicalRecordsService.deleteMedicalRecords(firstName, lastName);

        return new ResponseEntity<HttpStatus>(HttpStatus.ACCEPTED);
    }

    public void setMedicalRecordsService(MedicalRecordsService medicalRecordsService) {
        this.medicalRecordsService = medicalRecordsService;
    }
}

