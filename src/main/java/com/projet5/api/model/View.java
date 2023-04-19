package com.projet5.api.model;

import java.util.List;

public class View {

    public interface FirstName {
    }
    public interface LastName {
    }
    public interface MedicalRecordsFirstName{
    }
    public interface MedicalRecordsLastName{
    }
    public interface AddressPerson {
    }
    public interface City{
    }
    public interface Zip{
    }
    public interface Phone {
    }
    public interface Email{
    }
    public interface Birthdate{
    }
    public interface Medications{
    }
    public interface Allergies{
    }
    public interface AddressFireStation{
    }
    public interface StationNumber{
    }
    public interface NumberOfChildren{
    }
    public interface NumberOfAdults{
    }
    public interface Age{
    }

    public interface FireStations{
    }

    public interface PersonFirstNameAndLastNameOnly extends FirstName, LastName{
    }

    public interface PersonSearch extends FirstName, LastName, AddressPerson, Age, Email, MedicalRecordsOnly{
    }

    public interface PersonSearchAndPersonsWithSameLastName extends PersonSearch, PersonFirstNameAndLastNameOnly{
    }
    public interface MedicalRecordsOnly extends Medications, Allergies{
    }
    public interface ListOfPersonWithMedicalRecords extends FirstName, LastName, Phone, Age, MedicalRecordsOnly , StationNumber{
    }
    public interface PeopleCoveredByFireStationNumber extends FirstName, LastName, AddressPerson, Phone {
    }
    public interface PeopleCoveredByFireStationAndNumberOfChildren extends PeopleCoveredByFireStationNumber, NumberOfChildren, NumberOfAdults{
    }
    public interface ChildrenByAddressAndOtherMemberOfFamily extends FirstName, LastName, Age{
    }
    public interface MedicalRecordsFrontPersonFirstName extends FirstName, MedicalRecordsOnly{
    }
    public interface FamilysPersonsCoveredByStationNumber extends MedicalRecordsFrontPersonFirstName, LastName, Age, Phone{
    }


}