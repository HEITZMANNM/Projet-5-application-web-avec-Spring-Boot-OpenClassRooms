package com.projet5.api;

import com.projet5.api.model.FireStationNumberAndPersonsByAddress;
import com.projet5.api.model.Persons;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FireStationNumberAndPersonsByAddressModelTest {

    @Test
    public void testHashCode()
    {
        FireStationNumberAndPersonsByAddress fireStationNumberAndPersonsByAddress = new FireStationNumberAndPersonsByAddress();
        fireStationNumberAndPersonsByAddress.setFireStationNumber(3);
        List<Persons> listOfPersonWithMedicalRecords = new ArrayList<>();
        Persons person = new Persons();
        person.setAddress("11 way stone");
        person.setAge(11);
        person.setCity("Montana city");
        person.setEmail("John@gmail.fr");
        person.setLastName("Dutton");
        person.setFirstName("John");
        person.setMedicalRecords(null);
        listOfPersonWithMedicalRecords.add(person);
        fireStationNumberAndPersonsByAddress.setListOfPersonWithMedicalRecords(listOfPersonWithMedicalRecords);

        assertNotNull(fireStationNumberAndPersonsByAddress.hashCode());

    }

    @Test
    public void testEquals()
    {
        FireStationNumberAndPersonsByAddress fireStationNumberAndPersonsByAddress = new FireStationNumberAndPersonsByAddress();
        fireStationNumberAndPersonsByAddress.setFireStationNumber(3);
        List<Persons> listOfPersonWithMedicalRecords = new ArrayList<>();
        Persons person = new Persons();
        person.setAddress("11 way stone");
        person.setAge(11);
        person.setCity("Montana city");
        person.setEmail("John@gmail.fr");
        person.setLastName("Dutton");
        person.setFirstName("John");
        person.setMedicalRecords(null);
        listOfPersonWithMedicalRecords.add(person);
        fireStationNumberAndPersonsByAddress.setListOfPersonWithMedicalRecords(listOfPersonWithMedicalRecords);

        FireStationNumberAndPersonsByAddress fireStationNumberAndPersonsByAddress2 = new FireStationNumberAndPersonsByAddress();
        fireStationNumberAndPersonsByAddress2.setFireStationNumber(3);
        List<Persons> listOfPersonWithMedicalRecords2 = new ArrayList<>();
        Persons person2 = new Persons();
        person2.setAddress("11 way stone");
        person2.setAge(11);
        person2.setCity("Montana city");
        person2.setEmail("John@gmail.fr");
        person2.setLastName("Dutton");
        person2.setFirstName("John");
        person2.setMedicalRecords(null);
        listOfPersonWithMedicalRecords2.add(person2);
        fireStationNumberAndPersonsByAddress2.setListOfPersonWithMedicalRecords(listOfPersonWithMedicalRecords2);

        FireStationNumberAndPersonsByAddress fireStationNumberAndPersonsByAddress3 = new FireStationNumberAndPersonsByAddress();
        fireStationNumberAndPersonsByAddress.setFireStationNumber(3);
        List<Persons> listOfPersonWithMedicalRecords3 = new ArrayList<>();
        Persons person3 = new Persons();
        person3.setAddress("13 way stone");
        person3.setAge(11);
        person3.setCity("Montana city");
        person3.setEmail("John@gmail.fr");
        person3.setLastName("Dutton");
        person3.setFirstName("John");
        person3.setMedicalRecords(null);
        person3.setZip(11);
        listOfPersonWithMedicalRecords3.add(person3);
        fireStationNumberAndPersonsByAddress3.setListOfPersonWithMedicalRecords(listOfPersonWithMedicalRecords3);



        assertTrue(fireStationNumberAndPersonsByAddress.equals(fireStationNumberAndPersonsByAddress));

        assertTrue(fireStationNumberAndPersonsByAddress.equals(fireStationNumberAndPersonsByAddress2));

        fireStationNumberAndPersonsByAddress2 = null;

        assertFalse(fireStationNumberAndPersonsByAddress.equals(fireStationNumberAndPersonsByAddress2));

        assertFalse((new FireStationNumberAndPersonsByAddress().equals(fireStationNumberAndPersonsByAddress)));

        assertFalse(fireStationNumberAndPersonsByAddress.equals(fireStationNumberAndPersonsByAddress3));

        fireStationNumberAndPersonsByAddress3.setListOfPersonWithMedicalRecords(listOfPersonWithMedicalRecords);

        fireStationNumberAndPersonsByAddress3.setFireStationNumber(11);

        assertFalse(fireStationNumberAndPersonsByAddress.equals(fireStationNumberAndPersonsByAddress3));
    }

    @Test
    public void testToString()
    {
        FireStationNumberAndPersonsByAddress fireStationNumberAndPersonsByAddress = new FireStationNumberAndPersonsByAddress();
        fireStationNumberAndPersonsByAddress.setFireStationNumber(3);
        List<Persons> listOfPersonWithMedicalRecords = new ArrayList<>();
        Persons person = new Persons();
        person.setAddress("11 way stone");
        person.setAge(11);
        person.setCity("Montana city");
        person.setEmail("John@gmail.fr");
        person.setLastName("Dutton");
        person.setFirstName("John");
        person.setMedicalRecords(null);
        listOfPersonWithMedicalRecords.add(person);
        fireStationNumberAndPersonsByAddress.setListOfPersonWithMedicalRecords(listOfPersonWithMedicalRecords);


        assertNotNull(fireStationNumberAndPersonsByAddress.toString());
    }
}
