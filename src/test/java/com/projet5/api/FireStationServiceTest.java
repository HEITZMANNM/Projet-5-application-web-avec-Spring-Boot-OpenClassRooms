package com.projet5.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.projet5.api.model.FireStationNumberAndPersonsByAddress;
import com.projet5.api.model.FireStations;
import com.projet5.api.model.PeopleCoveredByFireStationAndNumberOfChildren;
import com.projet5.api.model.Persons;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import com.projet5.api.service.FireStationsService;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class FireStationServiceTest
{

    @Mock(lenient = true)
    private  JSONReaderFromURLIMPL jsonReaderFromURLIMPL;

    private List<FireStations> listOfFireStationByNumber3;

    private List<FireStations> listOfFireStationByNumber4;

    private List<Persons> listOfPersonsByAddress;

    @InjectMocks
    private FireStationsService fireStationsService = new FireStationsService();

    //create fireStations and List of people used to test the different method of fireStationService
    @BeforeEach
    public void setUp() throws JSONException, JsonProcessingException
    {
        listOfFireStationByNumber3 = new ArrayList<>();
        listOfFireStationByNumber4 = new ArrayList<>();
        listOfPersonsByAddress = new ArrayList<>();

        int stationNumber = 3;
        String addressOne = "11 way of Yellowstone";
        String addressTwo = "15 street of Montana";

        FireStations fireStationOne = new FireStations();
        fireStationOne.setStation(stationNumber);
        fireStationOne.setAddress(addressOne);

        FireStations fireStationTwo = new FireStations();
        fireStationTwo.setAddress(addressTwo);
        fireStationTwo.setStation(stationNumber);



        listOfFireStationByNumber3.add(fireStationOne);
        listOfFireStationByNumber3.add(fireStationTwo);

        int stationNumber4 = 4;
        String addressThree = "22 high street";

        FireStations fireStationsThree = new FireStations();
        fireStationsThree.setStation(stationNumber4);
        fireStationsThree.setAddress(addressThree);

        listOfFireStationByNumber4.add(fireStationsThree);


        Persons child = new Persons();
        Persons father = new Persons();
        Persons mother = new Persons();
        Persons aunt = new Persons();

        child.setAddress(addressOne);
        child.setFirstName("Beth");
        child.setLastName("Dutton");
        child.setAge(13);
        child.setCity("MontanaCity");


        father.setAddress(addressOne);
        father.setFirstName("John");
        father.setLastName("Dutton");
        father.setAge(43);
        father.setCity("MontanaCity");

        mother.setAddress(addressOne);
        mother.setFirstName("Marta");
        mother.setLastName("Dutton");
        mother.setAge(41);
        mother.setCity("MontanaCity");

        aunt.setAddress(addressTwo);
        aunt.setFirstName("Lisa");
        aunt.setLastName("Dutton");
        aunt.setAge(40);
        aunt.setCity("Havre");


        listOfPersonsByAddress.add(child);
        listOfPersonsByAddress.add(father);
        listOfPersonsByAddress.add(mother);


        when(jsonReaderFromURLIMPL.getFireStationByStationNumber(anyInt())).thenReturn(listOfFireStationByNumber4);
        when(jsonReaderFromURLIMPL.getAllPersonsByAddress(anyString())).thenReturn(listOfPersonsByAddress);
        when(jsonReaderFromURLIMPL.getFireStationByAddress(anyString())).thenReturn(fireStationsThree);
    }

    //control if the method getAddressCoveredByFireStation() return a list of address covered by a fireStation
    @Test
    public void testToFindTheAddressCoveredByASelectedFireStation() throws JSONException, JsonProcessingException
    {
        List<String> listOfAddressCovered =  fireStationsService.getAddressCoveredByFireStation(listOfFireStationByNumber3);

        assertEquals(listOfAddressCovered.size(), 2);
    }


    @Test
    public void testToFindThePeopleCoveredByStationNumberAndTheNumberOfChildren() throws JSONException, JsonProcessingException
    {
        PeopleCoveredByFireStationAndNumberOfChildren peopleCoveredByFireStationAndNumberOfChildren = fireStationsService.getPersonsCoveredByFireStationNumberAndNumberOfChildren(3);

        assertEquals(peopleCoveredByFireStationAndNumberOfChildren.getNumberOfChildren(), 1);
        assertEquals(peopleCoveredByFireStationAndNumberOfChildren.getListOfPersonsCovered().size(), 3);
    }

    @Test
    public void testToFindPeopleCoveredByStationNumber() throws JSONException, JsonProcessingException
    {
        List<Persons> listOfPersonsCoveredByStationNumber = fireStationsService.getPersonsCoveredByFireStationNumber(3);

        assertEquals(listOfPersonsCoveredByStationNumber.size(), 3);
    }

    @Test
    public void testToFindAllPersonsWhoLiveAtTheAddressAndFireStationWhichCoverThem() throws JSONException, JsonProcessingException
    {
        FireStationNumberAndPersonsByAddress fireStationNumberAndPersonsByAddress = fireStationsService.getPersonsWhoLiveAtTheAddressAndFireStationWhichCoverThem("22 high street");

        assertEquals(fireStationNumberAndPersonsByAddress.getFireStationNumber(), 4);
        assertEquals(fireStationNumberAndPersonsByAddress.getListOfPersonWithMedicalRecords().size(), 3);
    }

    @Test
    public void testToFindAllFamiliesCoveredByFireStationNumber() throws JSONException, JsonProcessingException
    {
        List<Integer> stations = new ArrayList<>();
        stations.add(4);
        List<List<Persons>> listOfFamiliesCovered = fireStationsService.getFamiliesCoveredByFireStationNumber(stations);

        assertEquals(listOfFamiliesCovered.size(), 1);
        assertEquals(listOfFamiliesCovered.get(0).size(), 3);
    }
}
