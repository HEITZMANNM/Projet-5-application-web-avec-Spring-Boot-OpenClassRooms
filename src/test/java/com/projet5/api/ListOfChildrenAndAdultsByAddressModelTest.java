package com.projet5.api;

import com.projet5.api.model.ListOfChildrenAndAdultsByAddress;
import com.projet5.api.model.Persons;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ListOfChildrenAndAdultsByAddressModelTest {

    @Test
    public void testHashCode()
    {
        ListOfChildrenAndAdultsByAddress list = new ListOfChildrenAndAdultsByAddress();
        List<Persons> listOfAdults = new ArrayList<>();
        Persons adulte = new Persons();
        adulte.setLastName("Dutton");
        adulte.setFirstName("John");
        listOfAdults.add(adulte);
        Persons child = new Persons();
        child.setFirstName("Bteh");
        child.setLastName("Meyer");
        List<Persons> listOfChildren = new ArrayList<>();
        listOfChildren.add(child);
        list.setListOfAdults(listOfAdults);
        list.setListOfChildren(listOfChildren);

        assertNotNull(list.hashCode());

    }

    @Test
    public void testEquals()
    {
        ListOfChildrenAndAdultsByAddress list = new ListOfChildrenAndAdultsByAddress();
        List<Persons> listOfAdults = new ArrayList<>();
        Persons adulte = new Persons();
        adulte.setLastName("Dutton");
        adulte.setFirstName("John");
        listOfAdults.add(adulte);
        Persons child = new Persons();
        child.setFirstName("Bteh");
        child.setLastName("Meyer");
        List<Persons> listOfChildren = new ArrayList<>();
        listOfChildren.add(child);
        list.setListOfAdults(listOfAdults);
        list.setListOfChildren(listOfChildren);

        ListOfChildrenAndAdultsByAddress list2 = new ListOfChildrenAndAdultsByAddress();
        List<Persons> listOfAdults2 = new ArrayList<>();
        Persons adulte2= new Persons();
        adulte2.setLastName("Dutton");
        adulte2.setFirstName("John");
        listOfAdults2.add(adulte2);
        Persons child2 = new Persons();
        child2.setFirstName("Bteh");
        child2.setLastName("Meyer");
        List<Persons> listOfChildren2 = new ArrayList<>();
        listOfChildren2.add(child2);
        list2.setListOfAdults(listOfAdults2);
        list2.setListOfChildren(listOfChildren2);

        ListOfChildrenAndAdultsByAddress list3 = new ListOfChildrenAndAdultsByAddress();
        List<Persons> listOfAdults3 = new ArrayList<>();
        Persons adulte3= new Persons();
        adulte3.setLastName("Dutton");
        adulte3.setFirstName("John");
        listOfAdults3.add(adulte3);
        Persons child3 = new Persons();
        child3.setFirstName("Bob");
        child3.setLastName("Meyer");
        List<Persons> listOfChildren3 = new ArrayList<>();
        listOfChildren3.add(child3);
        list3.setListOfAdults(listOfAdults3);
        list3.setListOfChildren(listOfChildren3);

        assertTrue(list.equals(list));

        assertTrue(list.equals(list2));

        list2 = null;

        assertFalse(list.equals(list2));

        assertFalse((new ListOfChildrenAndAdultsByAddress().equals(list)));

        assertFalse(list.equals(list3));

        Persons childUpDated = list3.getListOfChildren().get(0);
        childUpDated.setFirstName("Bteh");
        List<Persons> listOfChildren3UpDated = list3.getListOfChildren();
        listOfChildren3UpDated.remove(0);
        listOfChildren3UpDated.add(childUpDated);
        list3.setListOfChildren(listOfChildren3UpDated);

        list3.setListOfAdults(null);

        assertFalse(list.equals(list3));
    }

    @Test
    public void testToString()
    {
        ListOfChildrenAndAdultsByAddress list = new ListOfChildrenAndAdultsByAddress();
        List<Persons> listOfAdults = new ArrayList<>();
        Persons adulte = new Persons();
        adulte.setLastName("Dutton");
        adulte.setFirstName("John");
        listOfAdults.add(adulte);
        Persons child = new Persons();
        child.setFirstName("Bteh");
        child.setLastName("Meyer");
        List<Persons> listOfChildren = new ArrayList<>();
        listOfChildren.add(child);
        list.setListOfAdults(listOfAdults);
        list.setListOfChildren(listOfChildren);

        assertNotNull(list.toString());
    }
}
