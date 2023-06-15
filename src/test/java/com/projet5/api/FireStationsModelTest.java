package com.projet5.api;

import com.projet5.api.model.FireStations;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FireStationsModelTest {

    @Test
    public void testHashCode()
    {
        FireStations fireStations = new FireStations();
        fireStations.setStation(1);
        fireStations.setAddress("11 way stone");

        assertNotNull(fireStations.hashCode());

    }

    @Test
    public void testEquals()
    {
        FireStations fireStations = new FireStations();
        fireStations.setStation(1);
        fireStations.setAddress("11 way stone");

        FireStations fireStations2 = new FireStations();
        fireStations2.setStation(1);
        fireStations2.setAddress("11 way stone");


        FireStations fireStations3 = new FireStations();
        fireStations3.setStation(1);
        fireStations3.setAddress("13 way stone");

        assertTrue(fireStations.equals(fireStations));

        assertTrue(fireStations.equals(fireStations2));

        fireStations2 = null;

        assertFalse(fireStations.equals(fireStations2));

        assertFalse((new FireStations().equals(fireStations)));

        assertFalse(fireStations.equals(fireStations3));

        fireStations3.setAddress("11 way stone");

        fireStations3.setStation(12);

        assertFalse(fireStations.equals(fireStations3));
    }

    @Test
    public void testToString()
    {
        FireStations fireStations = new FireStations();
        fireStations.setStation(1);
        fireStations.setAddress("11 way stone");

        assertNotNull(fireStations.toString());
    }



}
