package com.projet5.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.projet5.api.model.FireStations;
import com.projet5.api.model.MedicalRecords;
import com.projet5.api.service.MedicalRecordsService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void getAllMedicalRecords()
    {
        medicalRecordsService.getAllMedicalRecords();
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
    public void upDateMedicalRecords(@RequestBody MedicalRecords medicalRecord)
    {
        medicalRecordsService.upDateMedicalRecords(medicalRecord);
    }

    @DeleteMapping("/medicalRecord")
    public void deleteMedicalRecords(@RequestParam(name = "firstName") String firstName, @RequestParam(name = "lastName") String lastName)
    {
        medicalRecordsService.deleteMedicalRecords(firstName, lastName);
    }
}
