package com.projet5.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class Persons {

    @JsonView(View.FirstName.class)
    private String firstName;

    @JsonView(View.LastName.class)
    private String lastName;

    @JsonView(View.AddressPerson.class)
    private String address;

    @JsonView(View.City.class)
    private String city;

    @JsonView(View.Zip.class)
    private int zip;

    @JsonView(View.Phone.class)
    private String phone;

    @JsonView(View.Email.class)
    private String email;

    @JsonView(View.FireStations.class)
    private FireStations fireStations;

    @JsonView(View.MedicalRecordsOnly.class)
    private MedicalRecords medicalRecords;

    @JsonView(View.Age.class)
    private int age;

    public Persons(String firstName, String lastName, String address, String city, int zip, String phone, String email, FireStations fireStations, MedicalRecords medicalRecords, int age)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city= city;
        this.zip= zip;
        this.phone= phone;
        this.email= email;
        this.fireStations = fireStations;
        this.medicalRecords = medicalRecords;
        this.age = age;
    }

    public Persons()
    {
    }

//     Method
//     Creating toString
    @Override public String toString()
    {
        // Returning attributes of person
        return "persons [firstName :"
                + firstName
                + "lastName :" + lastName
                + "address :" + address
                + "city :"+ city
                + "zip :"+ zip
                +"phone :"+ phone
                + "email :"+ email + "]";
    }

}
