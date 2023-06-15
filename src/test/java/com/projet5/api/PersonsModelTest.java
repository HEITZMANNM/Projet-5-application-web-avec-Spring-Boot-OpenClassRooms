package com.projet5.api;

import com.projet5.api.model.Persons;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PersonsModelTest {


    @Test
    public void testHashCode()
    {
        Persons person = new Persons();
        person.setAddress("11 way stone");
        person.setAge(11);
        person.setCity("Montana city");
        person.setEmail("John@gmail.fr");
        person.setLastName("Dutton");
        person.setFirstName("John");
        person.setMedicalRecords(null);

        assertNotNull(person.hashCode());

    }

    @Test
    public void testEquals()
    {
        Persons person = new Persons();
        person.setAddress("11 way stone");
        person.setAge(11);
        person.setCity("Montana city");
        person.setEmail("John@gmail.fr");
        person.setLastName("Dutton");
        person.setFirstName("John");
        person.setMedicalRecords(null);
        person.setZip(11);

        Persons person2 = new Persons();
        person2.setAddress("11 way stone");
        person2.setAge(11);
        person2.setCity("Montana city");
        person2.setEmail("John@gmail.fr");
        person2.setLastName("Dutton");
        person2.setFirstName("John");
        person2.setMedicalRecords(null);
        person2.setZip(11);


        Persons person3 = new Persons();
        person3.setAddress("13 way stone");
        person3.setAge(11);
        person3.setCity("Montana city");
        person3.setEmail("John@gmail.fr");
        person3.setLastName("Dutton");
        person3.setFirstName("John");
        person3.setMedicalRecords(null);
        person3.setZip(11);

        assertTrue(person.equals(person));

        assertTrue(person.equals(person2));

        person2 = null;

        assertFalse(person.equals(person2));

        assertFalse((new Persons().equals(person)));

        assertFalse(person.equals(person3));

        person3.setAddress("11 way stone");

        person3.setZip(12);

        assertFalse(person.equals(person3));
    }

    @Test
    public void testToString()
    {
        Persons person3 = new Persons();
        person3.setAddress("13 way stone");
        person3.setAge(11);
        person3.setCity("Montana city");
        person3.setEmail("John@gmail.fr");
        person3.setLastName("Dutton");
        person3.setFirstName("John");
        person3.setMedicalRecords(null);
        person3.setZip(11);

        assertNotNull(person3.toString());
    }

}
