package com.projet5.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class FireStations {

    @JsonView(View.AddressFireStation.class)
    private String address;

    @JsonView(View.StationNumber.class)
    private int station;
}
