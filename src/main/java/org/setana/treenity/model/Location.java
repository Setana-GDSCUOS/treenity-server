package org.setana.treenity.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {

    private double longitude;
    private double latitude;

    public Location(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
