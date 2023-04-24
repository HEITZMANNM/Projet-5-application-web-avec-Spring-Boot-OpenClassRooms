package com.projet5.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.projet5.api.controller.PersonsController;
import com.projet5.api.model.Persons;
import com.projet5.api.model.View;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import com.projet5.api.service.PersonsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonsController.class)
public class PersonsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PersonsService personsService;

    @MockBean
    private JSONReaderFromURLIMPL jsonReaderFromURLIMPL;

    @Test
    public void testGetChildrenAtTheAddressAndOtherMemberOfFamily() throws Exception {
        mockMvc.perform(get("/childAlert?address=1509 Culver St"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPersonInfo() throws Exception {
        mockMvc.perform(get("/personInfo?firstName=John&lastName=Boyd")).andExpect(status().isOk());
    }

    @Test
    public void testGetEmailOfPersonsWhoLiveAtSelectedCity() throws Exception {
        mockMvc.perform(get("/communityEmail?city=Culver")).andExpect(status().isOk());
    }

//    @Test
//    public void testPostNewPerson() throws Exception {
//
//        List<Persons> listOfPersons = new ArrayList<>();
//        Persons personToSave = new Persons();
//        personToSave.setLastName("Dutton");
//        personToSave.setLastName("John");
//        personToSave.setCity("Montana city");
//
//        when(jsonReaderFromURLIMPL.getPersons()).thenReturn(listOfPersons);
//
//        mockMvc.perform(post("/person", personToSave)).andExpect(status().isOk());
//    }
}
