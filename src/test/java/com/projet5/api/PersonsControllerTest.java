package com.projet5.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet5.api.controller.PersonsController;
import com.projet5.api.model.Persons;
import com.projet5.api.model.View;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import com.projet5.api.service.PersonsService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

//import static org.hamcrest.Matchers.any;
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


//        when(jsonReaderFromURLIMPL.getPersons()).thenReturn(listOfPersons);

//        mockMvc.perform(post("/person", personToSave)).andExpect(status().isCreated());
//
//
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
//
//        when(jsonReaderFromURLIMPL.saveNewPerson(any(Persons.class).thenReturn(true);
//
//
//        ResponseEntity<Object> responseEntity = personsService.addANewPerson(personToSave);
//
//        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
//        assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");

    }




