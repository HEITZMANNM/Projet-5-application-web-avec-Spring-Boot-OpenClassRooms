package com.projet5.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MedicalRecords that = (MedicalRecords) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(birthdate, that.birthdate) && Objects.equals(medications, that.medications) && Objects.equals(allergies, that.allergies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthdate, medications, allergies);
    }
}
