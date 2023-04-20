package com.projet5.api;

import com.projet5.api.model.FireStations;
import com.projet5.api.repository.JSONReaderFromURLIMPL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class ApiApplication {

//    @Autowired
//    JSONReaderFromURLIMPL jsonReaderFromURLIMPL;


    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }


//    @Override
//    public void run(String... args) throws Exception {
//
//        FireStations fireStations = new FireStations();
//        fireStations.setAddress("");
//        fireStations.setStation(6);
//        jsonReaderFromURLIMPL.saveNewFireStation(fireStations);
//
//        System.out.println(jsonReaderFromURLIMPL.getListOAllFireStations());
//    }

}


