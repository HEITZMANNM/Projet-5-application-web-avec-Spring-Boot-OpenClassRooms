package com.projet5.api.service;

import com.projet5.api.model.ListOfChildrenAndAdultsByAddress;
import com.projet5.api.model.Persons;
import com.projet5.api.repository.IRepository;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public interface PersonsService {

    public void setJsonReaderFromURLIMPL(IRepository repository);

    public JSONReaderFromURLIMPL getJsonReaderFromURLIMPL();

    public ListOfChildrenAndAdultsByAddress getChildrenAtAddressAndTheOtherMemberOfFamily(String address) throws JSONException, IOException;

    public List<Persons> getPersonInfo(String firstName, String lastName);

    public List<Persons> getAllPersonsByCity(String city);

    public boolean addANewPerson(Persons person);

    public boolean deleteThePerson(String firstName, String lastName);

    public boolean updatePerson(Persons person);
}
