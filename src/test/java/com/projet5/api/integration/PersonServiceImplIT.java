package com.projet5.api.integration;

import com.projet5.api.model.Persons;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import com.projet5.api.service.PersonsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersonServiceImplIT {
    private static PersonsServiceImpl personsService;

    private static JSONReaderFromURLIMPL jsonReaderFromURLIMPL;


    @BeforeEach
    public void setUp()
    {
        personsService = new PersonsServiceImpl();
        jsonReaderFromURLIMPL = new JSONReaderFromURLIMPL();

        personsService.setJsonReaderFromURLIMPL(jsonReaderFromURLIMPL);
    }

    //test to save a new Person
    @Test
    public void testToSaveANewPerson()
    {
        Persons personToSave = new Persons("Jack", "Sparrow", "22 Tortuga street", "Caraïbe", 97451, "888-888-888", "JS@mail.com", null, null, 45);

        personsService.addANewPerson(personToSave);

        List<Persons> personSearch = jsonReaderFromURLIMPL.getPersonByLastName("Sparrow");

        assertEquals(personToSave.getCity(), personSearch.get(0).getCity());
    }

    //test to delete a person
    @Test
    public void testToDeleteAPerson()
    {
        Persons personToSave = new Persons("Jack", "Sparrow", "22 Tortuga street", "Caraïbe", 97451, "888-888-888", "JS@mail.com", null, null, 45);

        personsService.addANewPerson(personToSave);

        int listOfAllPersonsBeforeDeleteJack = jsonReaderFromURLIMPL.getPersons().size();

        personsService.deleteThePerson("Jack", "Sparrow");

        int listOfAllPersonAfterDeleteJack = jsonReaderFromURLIMPL.getPersons().size();

        assertEquals(listOfAllPersonsBeforeDeleteJack, listOfAllPersonAfterDeleteJack + 1);
    }

    //test to upDate a person's info
    @Test
    public void testToUpDatePersonsInfo()
    {
        Persons personToUpDate = new Persons("Jack", "Sparrow", "22 Tortuga street", "Caraïbe", 97451, "888-888-888", "JS@mail.com", null, null, 45);

        personsService.addANewPerson(personToUpDate);

        personToUpDate.setAddress("55 Fountain of Youth way");

        personsService.updatePerson(personToUpDate);

        List<Persons> personSearch = jsonReaderFromURLIMPL.getPersonByLastName("Sparrow");

        assertEquals(personSearch.get(0).getAddress(), "55 Fountain of Youth way");
    }
}
