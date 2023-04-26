package com.projet5.api;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projet5.api.controller.PersonsController;
import com.projet5.api.model.Persons;
import com.projet5.api.model.View;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import com.projet5.api.service.PersonsService;
import org.hamcrest.Matchers;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

//import static org.hamcrest.Matchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    public void schouldCreateMockMvc()
    {
        assertNotNull(mockMvc);
    }

    @Test
    public void testGetChildrenAtTheAddressAndOtherMemberOfFamily() throws Exception {

//        List<Persons>listOfAllPersons = new ArrayList<>();
//        listOfAllPersons.add(new Persons("Beth", "Dutton", "11 yellowstone way","Montana city",0, null, null, null, null, 10));
//
//        when(jsonReaderFromURLIMPL.getAllPersonsByAddress(anyString())).thenReturn(listOfAllPersons);


        mockMvc.perform(get("/childAlert?address=1509 Culver St"))
                .andExpect(status().isOk());
//                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName()").value("Beth"));
    }

    @Test
    public void testGetPersonInfo() throws Exception {
        mockMvc.perform(get("/personInfo?firstName=John&lastName=Boyd")).andExpect(status().isOk());
    }

    @Test
    public void testGetEmailOfPersonsWhoLiveAtSelectedCity() throws Exception {
        mockMvc.perform(get("/communityEmail?city=Culver")).andExpect(status().isOk());
    }

    @Test
    public void testPostNewPerson() throws Exception {


        mockMvc.perform( MockMvcRequestBuilders
                        .post("/person")
                        .content(asJsonString(new Persons("Beth", "Dutton", "11 yellowstone way","Montana city",0, null, null, null, null, 40)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    }




