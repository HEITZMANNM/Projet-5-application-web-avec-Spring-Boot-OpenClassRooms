package com.projet5.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MedicalRecords {

    @JsonView(View.MedicalRecordsFirstName.class)
    private String firstName;

    @JsonView(View.MedicalRecordsLastName.class)
    private String lastName;

    @JsonView(View.Birthdate.class)
    private String birthdate;

    @JsonView(View.Medications.class)
    private List<String> medications;

    @JsonView(View.Allergies.class)
    private List<String> allergies;
}
