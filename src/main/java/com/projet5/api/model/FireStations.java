package com.projet5.api.model;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class FireStations {

    @JsonView(View.AddressFireStation.class)
    private String address;

    @JsonView(View.StationNumber.class)
    private int station;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FireStations that = (FireStations) o;
        return station == that.station && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, station);
    }
}
