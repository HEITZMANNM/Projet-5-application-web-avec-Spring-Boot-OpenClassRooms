package com.projet5.api;

import com.projet5.api.model.PeopleCoveredByFireStationAndNumberOfChildren;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PeopleCoveredByFireStationAndNumberOfChildrenModelTest {

    @Test
    public void testHashCode()
    {
        PeopleCoveredByFireStationAndNumberOfChildren peopleCoveredByFireStationAndNumberOfChildren = new PeopleCoveredByFireStationAndNumberOfChildren();
        peopleCoveredByFireStationAndNumberOfChildren.setNumberOfChildren(2);
        peopleCoveredByFireStationAndNumberOfChildren.setNumberOfAdults(1);
        peopleCoveredByFireStationAndNumberOfChildren.setListOfPersonsCovered(null);

        assertNotNull(peopleCoveredByFireStationAndNumberOfChildren.hashCode());

    }

    @Test
    public void testEquals()
    {
        PeopleCoveredByFireStationAndNumberOfChildren peopleCoveredByFireStationAndNumberOfChildren = new PeopleCoveredByFireStationAndNumberOfChildren();
        peopleCoveredByFireStationAndNumberOfChildren.setNumberOfChildren(2);
        peopleCoveredByFireStationAndNumberOfChildren.setNumberOfAdults(1);
        peopleCoveredByFireStationAndNumberOfChildren.setListOfPersonsCovered(null);

        PeopleCoveredByFireStationAndNumberOfChildren peopleCoveredByFireStationAndNumberOfChildren2 = new PeopleCoveredByFireStationAndNumberOfChildren();
        peopleCoveredByFireStationAndNumberOfChildren2.setNumberOfChildren(2);
        peopleCoveredByFireStationAndNumberOfChildren2.setNumberOfAdults(1);
        peopleCoveredByFireStationAndNumberOfChildren2.setListOfPersonsCovered(null);

        PeopleCoveredByFireStationAndNumberOfChildren peopleCoveredByFireStationAndNumberOfChildren3 = new PeopleCoveredByFireStationAndNumberOfChildren();
        peopleCoveredByFireStationAndNumberOfChildren3.setNumberOfChildren(5);
        peopleCoveredByFireStationAndNumberOfChildren3.setNumberOfAdults(1);
        peopleCoveredByFireStationAndNumberOfChildren3.setListOfPersonsCovered(null);



        assertTrue(peopleCoveredByFireStationAndNumberOfChildren.equals(peopleCoveredByFireStationAndNumberOfChildren));

        assertTrue(peopleCoveredByFireStationAndNumberOfChildren.equals(peopleCoveredByFireStationAndNumberOfChildren2));

        peopleCoveredByFireStationAndNumberOfChildren2 = null;

        assertFalse(peopleCoveredByFireStationAndNumberOfChildren.equals(peopleCoveredByFireStationAndNumberOfChildren2));

        assertFalse((new PeopleCoveredByFireStationAndNumberOfChildren().equals(peopleCoveredByFireStationAndNumberOfChildren)));

        assertFalse(peopleCoveredByFireStationAndNumberOfChildren.equals(peopleCoveredByFireStationAndNumberOfChildren3));

        peopleCoveredByFireStationAndNumberOfChildren3.setNumberOfChildren(3);

        peopleCoveredByFireStationAndNumberOfChildren3.setNumberOfAdults(12);

        assertFalse(peopleCoveredByFireStationAndNumberOfChildren.equals(peopleCoveredByFireStationAndNumberOfChildren3));
    }

    @Test
    public void testToString()
    {
        PeopleCoveredByFireStationAndNumberOfChildren peopleCoveredByFireStationAndNumberOfChildren3 = new PeopleCoveredByFireStationAndNumberOfChildren();
        peopleCoveredByFireStationAndNumberOfChildren3.setNumberOfChildren(5);
        peopleCoveredByFireStationAndNumberOfChildren3.setNumberOfAdults(1);
        peopleCoveredByFireStationAndNumberOfChildren3.setListOfPersonsCovered(null);

        assertNotNull(peopleCoveredByFireStationAndNumberOfChildren3.toString());
    }

}
