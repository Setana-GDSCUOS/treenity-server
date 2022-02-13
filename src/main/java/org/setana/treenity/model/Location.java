package org.setana.treenity.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    private double longitude;
    private double latitude;

    public Location(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public boolean equals(Object object) {
        if (object == null || object.getClass() != this.getClass())
            return false;
        return ((Location)object).longitude == this.longitude &&
            ((Location)object).latitude == this.latitude;

    }

    public Point toPoint() {
        return new Point(longitude, latitude);
    }

}
